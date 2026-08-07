// The "else" in the dangling-else lookahead and in the if-else alternative
// must not match a prefix of a longer word. The first three statements abut
// the closing brace with no layout in between, so an unprotected "else"
// would match inside the identifier: elsewhere and else1 leave a remainder
// no rule can parse, and elseif leaves "if(y);", a valid statement, so the
// wrong split parses and shows up as an ambiguity. The last statement is the
// dangling else, which the layout-aware lookahead resolves across the
// whitespace: the else binds to the inner if.
class ElseBoundary {
    void m() {
        if (x) {}elsewhere();
        if (x) {}else1();
        if (x) {}elseif(y);
        if (x) {} else {}
        if (a) if (b) f(); else g();
    }
}
