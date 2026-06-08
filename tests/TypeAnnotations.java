import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@interface NonNull {}

@Target({ElementType.TYPE_USE})
@interface Tainted {}

@Target({ElementType.TYPE_USE})
@interface Readonly {}

class TypeAnnotations {

    // Declaration position: leading @A goes on FieldModifier (UnannType path)
    @NonNull String field = "x";

    // Embedded annotation between dots is fine in declaration position
    @NonNull Map.@Readonly Entry<@NonNull String, @Tainted Integer> nested;

    // Cast: leading annotation on Type
    Object castPrim(Object o) {
        return (@NonNull int) (Integer) o;
    }

    Object castRef(Object o) {
        return (@NonNull String) o;
    }

    // instanceof: leading annotation on Type
    boolean inst(Object o) {
        return o instanceof @NonNull String;
    }

    // Annotated wildcard bound (the type after extends/super)
    List<? extends @NonNull Number> upperBound;
    List<? super @Tainted Integer> lowerBound;

    // throws: annotated ClassType
    void thrws() throws @NonNull IOException, @Tainted RuntimeException {}

    // new: annotated ClassTypeToInstantiate (per-segment)
    Object newCls() {
        return new @NonNull String("x");
    }

    Object newNested() {
        return new java.util.@NonNull HashMap<String, Integer>();
    }

    // Method parameter: leading on VariableModifier path (UnannType)
    void params(@NonNull String s, @Tainted int i) {}

    // Local variable: leading on VariableModifier
    void locals() {
        @NonNull String s = "x";
        @Tainted int i = 0;
    }

    // Array bracket annotations in declaration position (uses UnannArrayType)
    int @NonNull [] primArr;
    int @NonNull [] @Readonly [] primArr2;
    @NonNull String @Readonly [] refArr;

    // Array bracket annotations in cast position (uses ArrayType)
    Object castArr(Object o) {
        return (int @NonNull []) o;
    }

    Object castArr2(Object o) {
        return (int @NonNull [] @Readonly []) o;
    }

    // Array bracket annotations in instanceof (uses ArrayType)
    boolean instArr(Object o) {
        return o instanceof int @NonNull [];
    }

    // Annotated array creation: per-dim-expr brackets
    int[] newPrimArr() {
        return new int @NonNull [10];
    }

    // Annotated array creation: trailing empty dim
    int[][] newPrimArr2() {
        return new int @NonNull [10] @Readonly [];
    }

    // Annotated wildcard (the ? itself)
    List<@NonNull ?> annotatedWildcard;
    Map<@NonNull ?, @Tainted ?> bothWildcards;

    // Annotated type parameters
    <@NonNull T> T annotatedTypeParam(T t) { return t; }

    // Annotated bounds, single
    <T extends @NonNull Number> T singleBound(T t) { return t; }

    // Annotated bounds, intersection — multiple annotations on different bounds
    <T extends @NonNull Comparable<@NonNull T> & @Readonly Cloneable> T multiBound(T t) {
        return t;
    }

    // Annotated return type after type parameters (the gap from JDK 9 corpus)
    public <T> @NonNull String annotatedReturn() { return null; }

    public <T extends Number> @NonNull T annotatedReturnGeneric(T t) { return t; }

    // Mixed: annotated return + annotated parameter
    <T> @NonNull List<@Tainted T> mixed(@NonNull T t) { return null; }
}
