import java.util.function.Function;

class SwitchLambdaTails {
    static final int K = 1;
    static final int Q = 2;

    Function<Integer, Integer> guards(Object o, boolean ready, boolean a, boolean b, boolean c) {
        return switch (o) {
            case String s when (Boolean) ready -> x -> x;
            case Integer i when a ? b : c -> x -> x;
            default -> x -> x;
        };
    }

    Function<Integer, Integer> constants(int i) {
        return switch (i) {
            case K > 0 ? 1 : Q -> x -> x;
            default -> x -> x;
        };
    }
}
