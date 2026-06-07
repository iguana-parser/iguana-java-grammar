import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

class MethodRefs {
    // Expression :: Identifier   (covers ExpressionName + Primary)
    Function<String, Integer> simpleName = String::length;
    Function<String, Integer> staticRef = Integer::parseInt;
    Function<Integer, Integer> bindThis = this::times;
    Function<Integer, Integer> bindExpr = (new MethodRefs())::times;
    Function<Map.Entry<String, Integer>, String> dottedName = Map.Entry::getKey;

    // ParameterizedMethodRef   (LHS has type arguments)
    Function<List<String>, Boolean> parameterized = List<String>::isEmpty;

    // ClassType :: TypeArguments? "new"
    Supplier<MethodRefs> ctor = MethodRefs::new;
    Supplier<java.util.ArrayList<String>> ctorWithTypeArgs = java.util.ArrayList::<String>new;

    // ArrayType :: "new"
    IntFunction<String[]> arrCtor = String[]::new;
    IntFunction<int[]> primArrCtor = int[]::new;

    int times(int x) { return x * 2; }

    // "super" :: Identifier
    void superRef() {
        Runnable r = super::toString;
    }

    // QualifiedIdentifier "." "super" :: Identifier
    class Inner {
        Runnable r = MethodRefs.super::toString;
    }
}
