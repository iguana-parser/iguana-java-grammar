class CaseComparison {
    int m(Boolean x, int a, int b) {
        return switch (x) {
            case a < b -> 1;
            default -> 0;
        };
    }
}
