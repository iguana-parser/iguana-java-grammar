// Java 16: record declarations, with a header of components and an optional body.
import java.util.List;

record Empty() {}

record Point(int x, int y) {}

record Pair<A, B>(A first, B second) implements Comparable<Pair<A, B>> {
    public int compareTo(Pair<A, B> other) {
        return 0;
    }
}

record Range(int lo, int hi) {
    // A compact canonical constructor has no parameter list.
    Range {
        if (lo > hi) {
            throw new IllegalArgumentException();
        }
    }

    static Range empty() {
        return new Range(0, 0);
    }

    int length() {
        return hi - lo;
    }

    record Nested(String name) {}
}

record Annotated(@Deprecated int a, List<String> names, int @T ... rest) {
    public Annotated(int a, List<String> names, int... rest) {
        this.a = a;
        this.names = names;
        this.rest = rest;
    }
}

record Explicit(String s) {
    @Deprecated
    public Explicit {
        s = s.trim();
    }
}

class RecordMembers {
    record Inner(int v) {}

    interface I {
        record InInterface(int v) {}
    }

    @interface A {
        record InAnnotation(int v) {}
    }

    void local() {
        record Local(int v) {}
        Local l = new Local(1);
    }
}

@java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
@interface T {}
