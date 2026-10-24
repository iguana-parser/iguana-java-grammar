// JLS 25 syntax allows a field declared with `_` instead of an identifier. Only §6.1, a
// semantic rule, rejects it.
class BareUnderscore {
    int _ = 9;
}
