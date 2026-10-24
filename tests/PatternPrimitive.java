// JLS 25 syntax allows a primitive type in a top-level type pattern. Only §14.30.1, a
// semantic rule, rejects it, and primitive patterns are a preview feature in Java 25.
class PatternPrimitive {
    boolean test(Object o) {
        return o instanceof int i;
    }
}
