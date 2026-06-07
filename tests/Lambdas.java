import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

class Lambdas {
    // Bare identifier param
    IntUnaryOperator bare = x -> x + 1;

    // Empty parens
    Supplier<String> empty = () -> "hello";

    // Single inferred
    IntUnaryOperator singleInferred = (x) -> x * 2;

    // Multiple inferred
    IntBinaryOperator multiInferred = (x, y) -> x + y;

    // Single typed
    IntUnaryOperator singleTyped = (int x) -> x;

    // Multiple typed
    IntBinaryOperator multiTyped = (int x, int y) -> x + y;

    // Typed with modifier
    IntUnaryOperator finalTyped = (final int x) -> x;

    // Block body
    IntUnaryOperator block = (int x) -> { return x * x; };

    // Block body with multiple statements
    Function<Integer, Integer> multiStmt = x -> {
        int y = x * 2;
        return y + 1;
    };

    // Nested (curried)
    Function<Integer, Function<Integer, Integer>> curried = x -> y -> x + y;

    // Lambda body containing conditional
    BiFunction<Integer, Integer, Integer> withConditional = (x, y) -> x > y ? x : y;

    // Lambda returning a method reference
    Function<String, Function<String, Integer>> returnsMethodRef = s -> String::length;

    // Lambdas in method calls
    void usedAsArg(List<Integer> xs) {
        xs.forEach(x -> System.out.println(x));
        xs.removeIf(x -> x < 0);
        xs.sort((a, b) -> a - b);
    }
}
