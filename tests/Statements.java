class Statements {
    void test() {
        // Empty
        ;

        // Block
        { int local = 1; }

        // Local variable declaration
        int a = 1;
        final int b = 2;
        int c, d, e;
        int f = 1, g = 2;

        // Java 10: local variable type inference (var)
        var inferred = 1;
        var built = new java.util.ArrayList<String>();

        // Expression statement
        x = 1;
        x++;

        // if / else / chain
        if (x > 0) x = 1;
        if (x > 0) x = 1; else x = -1;
        if (x > 0) { x = 1; } else if (x < 0) { x = -1; } else { x = 0; }

        // while
        while (x < 10) x++;
        while (x < 10) { x++; }

        // do-while
        do x++; while (x < 10);
        do { x++; } while (x < 10);

        // basic for
        for (int i = 0; i < 10; i++) x += i;
        for (;;) break;
        for (int i = 0, j = 9; i < j; i++, j--) x = i + j;
        for (var i = 0; i < 10; i++) x += i; // Java 10: var

        // enhanced for
        for (int v : xs) x += v;
        for (Integer v : ints) x += v;
        for (var v : xs) x += v; // Java 10: var

        // switch (Java 1-7 form)
        switch (x) {
            case 0:
                y = 0;
                break;
            case 1:
            case 2:
                y = 12;
                break;
            default:
                y = -1;
        }

        // labeled
        outer:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + j > 5) break outer;
                if (i == j) continue outer;
            }
        }

        // break / continue (unlabeled)
        for (int i = 0; i < 10; i++) {
            if (i == 5) break;
            if (i % 2 == 0) continue;
        }

        // synchronized
        synchronized (lock) { x++; }

        // throw
        throw new IllegalStateException("bad");
    }

    void exceptions() {
        // try / catch
        try { x = 1; } catch (RuntimeException e) { x = -1; }

        // try / finally
        try { x = 1; } finally { y = 0; }

        // try / catch / finally with multiple catches
        try { x = 1; }
        catch (ArithmeticException e) { x = -1; }
        catch (Exception e) { x = -2; }
        finally { y = 0; }

        // multi-catch (Java 7)
        try { x = 1; } catch (ArithmeticException | NullPointerException e) { x = -1; }

        // try-with-resources
        try (java.io.InputStream in = null) { in.read(); }

        // try-with-resources, variable-access form (Java 9)
        try (in) { x = 1; }

        // try-with-resources with var (Java 10)
        try (var res = open()) { x = 1; }
    }

    void assertions() {
        assert x > 0;
        assert x > 0 : "x must be positive";
    }

    int returns(int v) {
        return v;
    }

    void returnsVoid() {
        return;
    }
}
