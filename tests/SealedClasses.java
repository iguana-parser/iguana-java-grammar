// Java 17: sealed classes and interfaces, and their permitted subclasses.
sealed interface Shape permits Circle, Square, Shapes.Polygon, Open {}

final class Circle implements Shape {}

non-sealed class Square implements Shape {}

class Shapes {
    static sealed abstract class Polygon implements Shape permits Triangle {}

    static final class Triangle extends Polygon {}
}

sealed class Expr {}

final class Num extends Expr {}

non-sealed interface Open extends Shape {}

record Unit() implements Shape2 {}

sealed interface Shape2 permits Unit {}

abstract sealed class WithTypes<T> extends Object implements Comparable<T> permits WithTypes.Leaf {
    static final class Leaf<T> extends WithTypes<T> {
        public int compareTo(T other) {
            return 0;
        }
    }
}
