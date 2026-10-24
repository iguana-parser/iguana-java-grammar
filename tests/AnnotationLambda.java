@interface A {
    Runnable value();
}

@A(() -> {})
class AnnotationLambda {}
