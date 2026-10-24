// Java 22: `_` declares a variable or pattern that has no name.
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

class UnnamedVariables {
    record Pair(Object first, Object second) {}

    int count(List<String> items) throws Exception {
        int total = 0;
        for (String _ : items) {
            total++;
        }
        for (int i = 0, _ = sideEffect(); i < 1; i++) {}
        var _ = sideEffect();
        int _ = sideEffect(), _ = sideEffect();
        try (var _ = open()) {
            total++;
        } catch (IllegalStateException | IllegalArgumentException _) {
            total--;
        }
        return total;
    }

    Function<String, Integer> one = _ -> 1;
    BiFunction<Integer, Integer, Integer> second = (_, b) -> b;
    BiFunction<Integer, Integer, Integer> typed = (Integer _, final Integer b) -> b;

    Object first(Object o) {
        if (o instanceof Pair(var a, _)) {
            return a;
        }
        if (o instanceof String _) {
            return o;
        }
        return switch (o) {
            case Pair(String _, Integer _), Pair(Integer _, String _) -> "mixed";
            case Pair(_, Pair(_, var inner)) -> inner;
            case Integer _, Long _ -> "number";
            default -> o;
        };
    }

    static int sideEffect() {
        return 0;
    }

    static AutoCloseable open() {
        return () -> {};
    }
}
