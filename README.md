# iguana-java-grammar

A Java grammar (Java 10) and the parser generated from it with
[Iguana](https://github.com/iguana-parser/iguana-rs).

The grammar is defined in [`java.iggy`](java.iggy).

## Links

- **Docs & CLI usage**: <https://iguana-parser.org>. Binary and library usage
  for a generated parser is at
  [/docs/generated-parser](https://iguana-parser.org/docs/generated-parser/).
- **Browse the grammar / playground**:
  [/grammars/java](https://iguana-parser.org/grammars/java/).
- **Iguana** (the parser generator):
  <https://github.com/iguana-parser/iguana-rs>.

## Developer notes

After editing `java.iggy`, regenerate the parser and rebuild:

```sh
iguana generate --grammar java.iggy --output .
cargo build --release
```

Two regression suites guard the grammar. Run both after a change:

```sh
# Per-feature golden files (tests/X.java vs committed tests/X.sexpr)
iguana-grammar --dir tests --ext java -n CompilationUnit --check-sexpr

# Real-world corpora (OpenJDK 9, Spring, Elasticsearch) vs corpus/ baselines
iguana-grammar --corpus-test
```

Regenerate golden files with `--regenerate-sexpr` and roll corpus baselines forward
with `--corpus-test --update` after an intentional change. See
[Developing grammars](https://iguana-parser.org/grammars/).

## License

Licensed under either of [MIT](LICENSE-MIT) or [Apache-2.0](LICENSE-APACHE) at
your option.

Copyright (c) 2026 Ali Afroozeh.
