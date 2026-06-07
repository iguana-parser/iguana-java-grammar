import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@interface NonNull {}

@Target({ElementType.TYPE_USE})
@interface Tainted {}

class ReceiverParameter {

    // Bare receiver
    void bare(ReceiverParameter this) {}

    // Annotated receiver
    void annotated(@NonNull ReceiverParameter this) {}

    // Receiver + regular params
    void withParams(@NonNull ReceiverParameter this, int x, String y) {}

    // Receiver + varargs
    void withVarargs(@NonNull ReceiverParameter this, int... xs) {}

    // Multiple annotations on the receiver type
    void multiAnnot(@NonNull @Tainted ReceiverParameter this) {}

    class Inner {
        // Qualified receiver for inner class
        void qualified(@NonNull ReceiverParameter ReceiverParameter.this) {}

        // Qualified receiver + regular params
        void qualifiedWithParams(@NonNull ReceiverParameter ReceiverParameter.this, int x) {}
    }
}
