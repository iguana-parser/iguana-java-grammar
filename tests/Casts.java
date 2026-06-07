import java.io.Serializable;
import java.util.Comparator;

class Casts {
    // Intersection casts (Java 8)
    Object intersectionTwo(Object o) {
        return (Comparable<String> & Serializable) o;
    }

    Object intersectionThree(Object o) {
        return (Comparator<String> & Serializable & Cloneable) o;
    }

    int intersectionThenCall(Object o) {
        return ((Comparable<Integer> & Serializable) o).compareTo(42);
    }

    // Reference cast (Java 7)
    String refCast(Object o) {
        return (String) o;
    }

    // Primitive cast (Java 7)
    int primCast(Object o) {
        return (int) (Integer) o;
    }

    // Bitwise & regression: must not be confused with intersection-cast `&`
    int bitwiseAnd(int a, int b) {
        return a & b;
    }

    int bitwiseAndAfterParen(int a, int b) {
        return (a) & b;
    }
}
