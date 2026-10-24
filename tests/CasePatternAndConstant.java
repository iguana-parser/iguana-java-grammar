class CasePatternAndConstant {
    int m(String o) {
        return switch (o) {
            case "a", String s -> 1;
        };
    }
}
