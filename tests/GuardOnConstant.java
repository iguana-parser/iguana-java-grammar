class GuardOnConstant {
    int m(int i, boolean b) {
        return switch (i) {
            case 1 when b -> 1;
            default -> 0;
        };
    }
}
