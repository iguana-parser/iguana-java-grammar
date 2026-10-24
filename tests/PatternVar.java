// JLS 25 syntax allows `var` in a top-level type pattern. Only §14.30.1, a semantic rule,
// rejects it.
class PatternVar {
    boolean test(Object o) {
        return o instanceof var v;
    }
}
