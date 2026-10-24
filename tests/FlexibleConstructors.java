// Java 25: statements may come before a constructor invocation.
class FlexibleConstructors {
    static class Base {
        Base(int value) {}
    }

    static class Checked extends Base {
        final int value;

        Checked(int value) {
            if (value < 0) {
                throw new IllegalArgumentException();
            }
            int doubled = value * 2;
            this.value = doubled;
            super(doubled);
            System.out.println(this.value);
        }

        Checked() {
            var defaultValue = 1;
            this(defaultValue);
        }
    }

    class Inner {
        Inner(int value) {}
    }

    class Sub extends Inner {
        Sub(FlexibleConstructors outer) {
            int value = 3;
            outer.super(value);
        }
    }

    record Range(int low, int high) {
        Range(int high) {
            int low = Math.min(0, high);
            this(low, high);
        }
    }
}
