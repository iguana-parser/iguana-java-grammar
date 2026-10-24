// JLS 25 syntax allows a variable arity parameter in any position. Only §8.4.1, a semantic
// rule, rejects it.
class VarargsNotLast {
    VarargsNotLast(String... names, int count) {}

    void m(int... values, int last) {}
}
