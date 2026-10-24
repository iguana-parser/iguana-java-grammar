class CaseMethodCall {
    int m(int i) {
        return switch (i) {
            case f(1) -> 1;
            default -> 0;
        };
    }

    static int f(int x) {
        return x;
    }
}
