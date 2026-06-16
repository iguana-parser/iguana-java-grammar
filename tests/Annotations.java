// Annotation type declarations

// Marker annotation
@interface Marker {}

// Annotation with single-element default
@interface SingleValue {
    String value();
}

// Annotation with named elements and defaults
@interface Named {
    String name();
    int priority() default 0;
    Class<?> impl() default Object.class;
}

// Annotation with array-valued elements
@interface Multi {
    String[] tags() default {};
    Class<?>[] classes() default {};
}

// Annotation with annotation-valued element
@interface Wrapper {
    Marker inner() default @Marker;
    Named[] named() default {};
}

// Annotation with enum-valued element
enum Level { LOW, MID, HIGH }

@interface WithEnum {
    Level level() default Level.MID;
}

// Meta-annotated annotation
@java.lang.annotation.Target({
    java.lang.annotation.ElementType.TYPE,
    java.lang.annotation.ElementType.METHOD
})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@interface Targeted {}

// Applied annotations covering shapes
@Marker
class Plain {}

@SingleValue("hi")
class WithSingle {}

@Named(name = "x", priority = 10)
class WithNamed {}

@Multi(tags = {"a", "b", "c"}, classes = {String.class, Integer.class})
class WithMulti {}

@Wrapper(inner = @Marker, named = {@Named(name = "x"), @Named(name = "y", priority = 5)})
class WithWrapper {}

// Repeating annotations (Java 8)
@SingleValue("a")
@SingleValue("b")
class Repeating {}

// Annotations on members
class WithMembers {
    @Deprecated int field;

    @Override
    public String toString() { return ""; }

    void method(@SingleValue("p") int param) {}

    void local() {
        @SuppressWarnings("unchecked")
        java.util.List raw = new java.util.ArrayList();
    }
}
