// Java 15: text blocks, and the \s escape sequence.
class TextBlocks {
    String empty = """
        """;

    String html = """
        <html>
            <body>"quoted" and ""twice"" quoted</body>
        </html>
        """;

    String escapes = """
        tab\there, newline\n, space\s, quote \""" inside, backslash \\
        joined \
        line, octal \0 \12 \377, unicode A
        """;

    String trailingQuote = """
        ends with a quote\"""";

    String concat = """
        a""" + """
        b""";

    String afterSpace = """   
        opening delimiter has trailing spaces
        """;

    String stringSpace = "a\sb";

    Object method = """
        x""".strip();
}
