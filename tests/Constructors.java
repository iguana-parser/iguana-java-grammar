class Constructors {
    int x;

    // Default constructor
    Constructors() {}

    // With single parameter
    Constructors(int x) {
        this.x = x;
    }

    // With multiple parameters
    Constructors(int x, int y) {
        this.x = x + y;
    }

    // Calling another constructor with this(...)
    Constructors(String s) {
        this(s.length());
    }

    // Calling super(...) explicitly
    Constructors(double d) {
        super();
        this.x = (int) d;
    }

    // Generic constructor
    <T> Constructors(T t, boolean ignored) {
        this.x = t.hashCode();
    }

    // Constructor with throws clause
    Constructors(java.io.File file) throws java.io.IOException {
        this.x = 0;
    }

    // Varargs constructor
    Constructors(int... xs) {
        for (int v : xs) this.x += v;
    }

    // With explicit access modifiers
    public Constructors(int x, int y, int z) {}
    protected Constructors(int x, int y, int z, int w) {}
    private Constructors(boolean b) {}

    // With annotation
    @Deprecated
    Constructors(char c) {}
}

class Derived extends Constructors {
    Derived() {
        super(0);
    }

    Derived(int x) {
        super(x, x);
    }

    // Qualified super for inner class scenario
    Derived(String s) {
        super(s);
    }
}
