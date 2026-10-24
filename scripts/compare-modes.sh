#!/usr/bin/env bash
# Parses every corpus file twice, once with the default parser and once with
# the same grammar generated in first-parse mode, and compares the parse
# trees.
#
# First-parse mode stops at the first derivation that spans the input, so it
# builds the same tree as the default mode only as long as the grammar is
# unambiguous over the corpus. The corpus test already checks that no file
# is ambiguous. This checks the stronger claim, that both modes build the
# same tree for every file.
#
# The comparison runs in two passes. The first pass parses every file with
# both parsers and compares one digest per file, which is cheap enough to
# run over the whole corpus. Only if some of the digests are different does
# the second pass run, and it writes out both trees for the files that
# differ. Everything the script writes stays in target/compare-modes, and a
# clean run leaves nothing behind.
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

DEFAULT_PARSER="target/release/iguana-java"
# An absolute path, because the first-parse parser is built from a temporary
# directory and writes its build log here.
OUT_DIR="$ROOT/target/compare-modes"
START="CompilationUnit"
# The dumps show every node of a tree, which is what the digest covers.
DISPLAY=(--show-layout --show-empty --show-wrappers)
# When the two modes disagree, the trees are written out for at most this
# many files. The rest are still listed in the digest output files, and a
# handful of examples is enough to see what kind of difference it is.
MAX_DUMPS=20

if [ ! -x "$DEFAULT_PARSER" ]; then
    echo "Build the parser first: cargo build --release" >&2
    exit 1
fi
if ! grep -q -- --digest <<< "$("$DEFAULT_PARSER" --help)"; then
    echo "This parser has no --digest, which the comparison needs." >&2
    echo "Regenerate against an iguana that provides it." >&2
    exit 1
fi

BUILD_DIR="$(mktemp -d)"
trap 'rm -rf "$BUILD_DIR"' EXIT

# The directory is cleared before the runs, because --digest does not write
# over a file that is already there, and a previous run's output would stop
# this one.
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Generating the first-parse parser..."
cp java.iggy "$BUILD_DIR/"
iguana generate --grammar "$BUILD_DIR/java.iggy" --output "$BUILD_DIR" \
    --bin-name iguana-java --first-parse true > /dev/null
if ! (cd "$BUILD_DIR" && cargo build --release > "$OUT_DIR/build.log" 2>&1); then
    echo "Building the first-parse parser failed:" >&2
    tail -n 20 "$OUT_DIR/build.log" >&2
    exit 1
fi
FIRST_PARSE_PARSER="$BUILD_DIR/target/release/iguana-java"
# The first-parse parser comes from the released iguana, so it can lack
# --digest even when the parser in this repository has it.
if ! grep -q -- --digest <<< "$("$FIRST_PARSE_PARSER" --help)"; then
    echo "The generated first-parse parser has no --digest." >&2
    echo "Its iguana-runtime is the published one; the comparison needs a release with --digest." >&2
    exit 1
fi
echo "Comparing every .java file under corpus/.cache..."
"$DEFAULT_PARSER" --dir corpus/.cache --ext java --start "$START" \
    --digest "$OUT_DIR/digest-default.txt"
"$FIRST_PARSE_PARSER" --dir corpus/.cache --ext java --start "$START" \
    --digest "$OUT_DIR/digest-first-parse.txt"

if diff -q "$OUT_DIR/digest-default.txt" "$OUT_DIR/digest-first-parse.txt" > /dev/null; then
    rm -rf "$OUT_DIR"
    echo "The two modes build the same tree for every file."
    exit 0
fi

# The second pass writes out the trees themselves. diff exits with 1 when
# the two files differ, which is the expected case here, and with 2 or more
# when something went wrong.
DIFF_STATUS=0
diff "$OUT_DIR/digest-default.txt" "$OUT_DIR/digest-first-parse.txt" > "$OUT_DIR/diff.txt" \
    || DIFF_STATUS=$?
if [ "$DIFF_STATUS" -gt 1 ]; then
    echo "diff failed with status $DIFF_STATUS" >&2
    exit "$DIFF_STATUS"
fi
awk -F'\t' '/^[<>]/ {print $3}' "$OUT_DIR/diff.txt" | sort -u > "$OUT_DIR/differing.txt"
COUNT=$(wc -l < "$OUT_DIR/differing.txt" | tr -d ' ')
echo "The two modes disagree on $COUNT files." >&2

# The s-expression shows the names and the shape of a tree, but it does not
# print spans, and the digest covers those as well. Two trees that differ
# only in where a token starts or ends would look identical in the dumps, so
# the JSON, which has a start and an end for every node, is written next to
# each one.
#
# The dumps are numbered rather than named after their file, because
# flattening a path into a file name collides: a/b_c.java and a_b/c.java
# both flatten to the same name, and the second dump would replace the
# first. index.txt says which number belongs to which file.
mkdir -p "$OUT_DIR/trees"
: > "$OUT_DIR/trees/index.txt"
n=0
head -n "$MAX_DUMPS" "$OUT_DIR/differing.txt" | while IFS= read -r file; do
    n=$((n + 1))
    name="$(printf '%03d' "$n")"
    printf '%s\t%s\n' "$name" "$file" >> "$OUT_DIR/trees/index.txt"
    for mode in default first-parse; do
        case "$mode" in
            default) parser="$DEFAULT_PARSER" ;;
            *) parser="$FIRST_PARSE_PARSER" ;;
        esac
        # Every parse writes its timing to stderr, which goes to a separate
        # file. Folded into the dump it would make two identical trees look
        # different, because the timings are never the same.
        "$parser" --start "$START" "${DISPLAY[@]}" "$file" \
            > "$OUT_DIR/trees/$name.$mode" 2> "$OUT_DIR/trees/$name.$mode.err" \
            < /dev/null || true
        "$parser" --start "$START" --quiet \
            --write-parse-tree "$OUT_DIR/trees/$name.$mode.json" "$file" \
            > /dev/null 2>&1 < /dev/null || true
        # The parser writes the JSON as a single line, which a diff cannot
        # point inside of, so it is reformatted when python3 is available.
        if command -v python3 > /dev/null; then
            python3 -m json.tool "$OUT_DIR/trees/$name.$mode.json" \
                "$OUT_DIR/trees/$name.$mode.json.tmp" > /dev/null 2>&1 \
                && mv "$OUT_DIR/trees/$name.$mode.json.tmp" "$OUT_DIR/trees/$name.$mode.json"
        fi
    done
done

{
    echo
    echo "Digest output files and trees are in $OUT_DIR:"
    echo "  digest-default.txt      one digest per file, default mode"
    echo "  digest-first-parse.txt  one digest per file, first-parse mode"
    echo "  diff.txt                the digests that differ"
    echo "  differing.txt           the $COUNT files that differ"
    echo "  trees/                  both trees, s-expression and JSON with spans"
    echo "  trees/index.txt         which number is which file"
    echo
    echo "To see one difference:"
    echo "  diff \"$OUT_DIR/trees/001.default\" \"$OUT_DIR/trees/001.first-parse\""
} >&2
exit 1
