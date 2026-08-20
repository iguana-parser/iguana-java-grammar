<h1 align="center">Java grammar for Iguana</h1>

A Java 11 grammar written in
[Iggy](https://iguana-parser.org/docs/grammar-definition/), based on the syntax
described in the
[Java Language Specification](https://docs.oracle.com/javase/specs/jls/se11/html/).
The grammar is defined in [`java.iggy`](java.iggy) and can be
[browsed or run in the playground](https://iguana-parser.org/grammars/java/).

The grammar has three notable design choices:

- **Expressions** use a natural, ambiguous form. Precedence and associativity
  select the intended tree without splitting expressions into a hierarchy of
  nonterminals. The [disambiguation documentation](https://iguana-parser.org/docs/grammar-definition/disambiguation/)
  explains the notation.
- **Layout** is the whitespace and comments that may occur between grammar
  symbols. The rule
  `Layout = (WhiteSpace | Comment)* !>> WhiteSpace !>> Comment` uses follow
  restrictions to prevent the repetition from stopping while more whitespace
  or another comment remains. This makes the choice between continuing and
  stopping decidable with one token of lookahead, so `Layout` is LL(1). Layout
  appears between nearly every pair of symbols, making this optimization
  particularly important for performance. The documentation covers
  [layout](https://iguana-parser.org/docs/grammar-definition/layout/) and the
  measured effect of the
  [LL(1) optimization](https://iguana-parser.org/docs/performance/#the-ll-1-optimization).
- **Contextual keywords** such as `var` and the module-system words are reserved
  only where the grammar expects them. The
  [single-phase parsing documentation](https://iguana-parser.org/docs/concepts/single-phase-parsing/)
  explains how token matching follows the grammar context.

## Development

The Rust sources under `src/` are generated from [`java.iggy`](java.iggy) and
should not be edited directly. After changing the grammar, regenerate the
parser and rebuild it:

```sh
iguana generate --grammar java.iggy --output .
cargo build --release
```

The repository has golden tests for individual language features and corpus
tests against OpenJDK, Spring, and Elasticsearch:

```sh
cargo run --release -- --dir tests --ext java --start CompilationUnit --check-sexpr
cargo run --release -- --corpus-test
```

See [Developing grammars](https://iguana-parser.org/grammars/) for the complete
workflow.

## License

Licensed under either of [MIT](LICENSE-MIT) or [Apache-2.0](LICENSE-APACHE) at
your option.

Copyright (c) 2026 Ali Afroozeh.

## Trademark

Java is a registered trademark of Oracle and/or its affiliates. This project
is not affiliated with or endorsed by Oracle.
