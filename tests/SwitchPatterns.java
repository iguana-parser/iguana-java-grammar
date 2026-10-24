// Java 21: pattern matching in switch, with guards and null labels.
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class SwitchPatterns {
    sealed interface Shape permits Circle, Square, Empty {}

    record Circle(double radius) implements Shape {}

    record Square(double side) implements Shape {}

    record Empty() implements Shape {}

    enum Color { RED, GREEN }

    static final int ONE = 1;

    static class Limits {
        static final int MAX = 10;
    }

    double area(Shape shape) {
        return switch (shape) {
            case Circle c when c.radius() > 10 -> -1;
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Square s -> s.side() * s.side();
            case Empty() -> 0;
        };
    }

    // A type argument after case belongs to the pattern's type, not to a comparison
    int size(Object o) {
        return switch (o) {
            case List<?> list when list.isEmpty() -> 0;
            case Map<?, ?> map -> map.size();
            case final String s -> s.length();
            case null -> -1;
            default -> 1;
        };
    }

    // The colon form, with null folded into the default label
    String describe(Object o) {
        switch (o) {
            case Integer i when i > 0:
                return "positive";
            case Integer i:
                return "integer";
            case null, default:
                return "other";
        }
    }

    // A qualified enum constant is a case constant, not a pattern
    int code(Color color) {
        return switch (color) {
            case Color.RED -> 1;
            case GREEN -> 2;
        };
    }

    // `when` is a keyword only between a pattern and its guard
    boolean when(Object o) {
        return switch (o) {
            case String when when when.isEmpty() -> true;
            default -> false;
        };
    }

    // A lambda as the rule's expression, after a guard
    Runnable task(Object o, boolean ready) {
        return switch (o) {
            case String s when ready -> () -> System.out.println(s);
            default -> () -> {};
        };
    }

    // Type arguments after case belong to the pattern's type, not to two comparisons
    int typeArguments(Map<String, Integer> map) {
        return switch (map) {
            case HashMap<String, Integer> hash -> hash.size();
            default -> 0;
        };
    }

    // A lambda as the rule's expression, after a guard that ends in an operator
    Function<Integer, Integer> guardedLambda(Object o, int limit) {
        return switch (o) {
            case Integer i when i > limit -> x -> x + i;
            default -> x -> x;
        };
    }

    // A lambda as the rule's expression, after constants that end in an operator
    Function<Integer, Integer> constantLambda(int code) {
        return switch (code) {
            case -ONE -> x -> -x;
            case Limits.MAX + ONE -> x -> x + ONE;
            default -> x -> x;
        };
    }
}
