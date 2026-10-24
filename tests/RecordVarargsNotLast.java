// JLS 25 syntax allows a variable arity record component in any position. Only §8.10.1, a
// semantic rule, rejects it.
record RecordVarargsNotLast(int... values, int last) {}
