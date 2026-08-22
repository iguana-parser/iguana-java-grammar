class SwitchExpressions {
    // Java 14: switch expression with arrow rules
    int arrow(int d) {
        return switch (d) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 0;
        };
    }

    // Several constants share one label
    int multiLabel(int d) {
        return switch (d) {
            case 1, 2, 3 -> 10;
            case 4, 5 -> 20;
            default -> 0;
        };
    }

    // The block and throw forms of a switch rule
    int blockAndThrow(int d) {
        return switch (d) {
            case 1 -> { yield 10; }
            case 2 -> throw new IllegalStateException("two");
            default -> { int t = d * 2; yield t; }
        };
    }

    // Colon form, where yield supplies the value
    int colonYield(int d) {
        return switch (d) {
            case 1:
            case 2:
                yield 12;
            case 3:
                yield (d + 1);
            default:
                yield 0;
        };
    }

    // Arrow rules in a switch statement rather than an expression
    void arrowStatement(int d) {
        switch (d) {
            case 1 -> x = 10;
            case 2 -> { x = 20; }
            default -> throw new IllegalStateException();
        }
    }

    // Colon-form labels accept multiple constants
    void colonStatement(int d) {
        switch (d) {
            case 1, 2:
                x = 12;
                break;
            default:
                x = 0;
        }
        switch (d) { }
    }

    // A switch expression is an operand, an argument, and a nesting site
    void positions(int a, int b) {
        int sum = switch (a) { default -> 1; } + 1;
        f(switch (a) { default -> 1; });
        int nested = switch (a) {
            default -> switch (b) { default -> 2; };
        };
        int ternary = switch (a) { default -> 1; } > 0 ? 1 : 0;
        int[] arr = { switch (a) { default -> 1; } };
    }

    // Java 14: yield is a restricted identifier, not a keyword, so names still work
    int yield = 5;

    void yieldAsName() {
        int yield = 1;
        yield = 2;
        yield++;
        yield += 3;
        int n = yield - 1;
        int m = -yield;
        this.yield(n);
        Thread.yield();
    }

    // Declaring a method named yield stays legal; only unqualified calls to it are barred
    void yield(int v) {}
}
