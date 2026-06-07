interface InterfaceMethods {
    // Classic abstract (Java 7)
    int oldStyle(int x);

    // Default method (Java 8)
    default int doubled(int x) {
        return x * 2;
    }

    // Static interface method (Java 8)
    static int zero() {
        return 0;
    }

    // Default + generic
    default <T> T identity(T t) {
        return t;
    }

    // Explicit public abstract
    public abstract String name();
}
