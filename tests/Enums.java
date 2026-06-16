// Basic enum
enum Color { RED, GREEN, BLUE }

// Trailing comma allowed
enum Trailing { A, B, C, }

// Trailing semicolon allowed (boundary between constants and body)
enum TrailingSemi { A, B, C; }

// Enum with members
enum Direction {
    NORTH, SOUTH, EAST, WEST;

    Direction opposite() { return this; }
}

// Enum with constructor and instance fields
enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6);

    final double mass;
    final double radius;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    double surfaceGravity() { return mass / (radius * radius); }
}

// Enum constants with class bodies (overriding methods)
enum Operation {
    PLUS {
        public int apply(int x, int y) { return x + y; }
    },
    MINUS {
        public int apply(int x, int y) { return x - y; }
    },
    TIMES {
        public int apply(int x, int y) { return x * y; }
    };

    public abstract int apply(int x, int y);
}

// Enum implementing interface
interface Doubler { int doubled(); }

enum SmallNumbers implements Doubler {
    ONE, TWO, THREE;

    public int doubled() { return ordinal() * 2; }
}

// Enum with annotations on constants
enum Annotated {
    @Deprecated A,
    B;
}
