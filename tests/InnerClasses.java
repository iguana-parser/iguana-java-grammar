class Outer {
    int field;

    // Static nested class
    static class StaticNested {
        int x;
        static int y;
    }

    // Inner (non-static) class
    class Inner {
        int x;

        // Reference to outer instance
        int sum() { return x + Outer.this.field; }
    }

    // Generic inner class
    class GenericInner<T> {
        T value;
    }

    // Inner enum (implicitly static)
    enum Level { LOW, HIGH }

    // Inner interface (implicitly static)
    interface Callback {
        void call();
    }

    // Inner annotation type (implicitly static)
    @interface Mark {}

    // Deeply nested
    static class A {
        static class B {
            static class C {
                int x;
            }
        }
    }

    void method() {
        // Local class
        class Local {
            int x;
            int local() { return x + field; }
        }
        Local l = new Local();

        // Anonymous class implementing interface
        Runnable r = new Runnable() {
            public void run() { field++; }
        };

        // Anonymous class extending abstract class
        Object o = new Object() {
            @Override
            public String toString() { return "anon"; }
        };

        // Anonymous class with constructor arguments
        Object list = new java.util.ArrayList<String>(10) {
            @Override
            public boolean add(String e) { return super.add(e.trim()); }
        };

        // Anonymous class with diamond (Java 9 supports diamond on anon classes)
        Object listDiamond = new java.util.ArrayList<>() {
            @Override
            public boolean add(String e) { return super.add(e.trim()); }
        };

        // Inner-class instance via qualified new
        Inner inner = this.new Inner();

        // Nested-static instance
        StaticNested nested = new StaticNested();

        // Reference to outer.this from a nested anonymous
        Runnable r2 = new Runnable() {
            public void run() {
                int captured = Outer.this.field;
            }
        };
    }
}
