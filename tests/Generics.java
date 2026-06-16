class Generics {
    // Generic class with single type parameter
    static class Box<T> { T value; }

    // Generic class with multiple type parameters
    static class Pair<A, B> { A first; B second; }

    // Bounded type parameter
    static class NumericBox<T extends Number> { T value; }

    // Intersection bound
    static class Constrained<T extends Number & Comparable<T> & java.io.Serializable> {}

    // Recursive bound
    static class Recursive<T extends Recursive<T>> {}

    // Generic method
    <T> T identity(T t) { return t; }

    // Generic method with bounded type
    <T extends Number> T pickNumber(T t) { return t; }

    // Generic method with multiple type parameters
    <K, V> Pair<K, V> make(K k, V v) { return null; }

    // Generic method with intersection bound
    <T extends Comparable<T> & Cloneable> T pickBoth(T t) { return t; }

    // Generic method invocation with explicit type witness
    void invoke() {
        Box<String> b = this.<String>genericNew();
    }

    <T> Box<T> genericNew() { return null; }

    // Wildcard uses
    java.util.List<?> wildcard;
    java.util.List<? extends Number> upperBound;
    java.util.List<? super Integer> lowerBound;

    // Nested generic types
    java.util.Map<String, java.util.List<Integer>> nested;
    java.util.List<Pair<String, java.util.List<Integer>>> deepNested;

    // Diamond
    java.util.List<String> diamondCreated = new java.util.ArrayList<>();

    // Raw type
    java.util.List rawList;

    // Type argument inferred by call site
    void callSite() {
        Box<String> b = identity(new Box<String>());
    }
}
