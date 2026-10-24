// Java 21: record patterns deconstruct a record into its components.
class RecordPatterns {
    record Point(int x, int y) {}

    record Line(Point start, Point end) {}

    record Box<T>(T content) {}

    static class Shapes {
        record Origin() {}
    }

    int instanceOf(Object o) {
        if (o instanceof Point(int x, int y)) {
            return x + y;
        }
        if (o instanceof Line(Point(var x1, var y1), Point end) && end.x() > x1) {
            return y1;
        }
        return o instanceof Box<?>(String s) ? s.length() : 0;
    }

    String inSwitch(Object o) {
        return switch (o) {
            case Point(final int x, int y) when x == y -> "diagonal";
            case Line(Point(var x, var y), Point(int a, int b)) -> "line";
            case Shapes.Origin() -> "origin";
            default -> "other";
        };
    }

    String unbox(Box<String> box) {
        return switch (box) {
            case Box<String>(var s) -> s;
        };
    }
}
