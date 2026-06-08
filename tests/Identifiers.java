class Identifiers {
    // Leading underscore still legal (only bare _ is reserved as of Java 9)
    int _foo = 1;
    int __bar = 2;
    int x_ = 3;
    int $z = 4;
    int $_$ = 5;

    void m() {
        int _foo_local = 6;
        String __ = "two underscores ok";
    }

    // Method names with underscores
    void _privateLike() {}
    void leading_underscore_method() {}
}
