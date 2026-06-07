import java.util.ArrayList;
import java.util.HashMap;

class NewExprs {
    // Simple, with trailing type arguments
    Object simple = new ArrayList<String>();

    // Diamond
    Object diamond = new ArrayList<>();

    // Multiple type arguments
    Object multi = new HashMap<String, Integer>();

    // Multiple type arguments with diamond
    Object multiDiamond = new HashMap<>();

    // No type arguments at all
    Object plain = new Object();

    // Dotted name (no intermediate type args, per JLS)
    Object dotted = new java.util.LinkedList<String>();

    // Explicit constructor type witness
    static <T> Object witness() {
        return new <T>NewExprs();
    }

    // Qualified new for inner class
    class Inner {}
    Object inner = new NewExprs().new Inner();

    // Anonymous class
    Object anon = new Object() {
        @Override
        public String toString() { return "anon"; }
    };

    // Anonymous class with diamond
    Object anonDiamond = new ArrayList<String>() {
        @Override
        public boolean isEmpty() { return true; }
    };
}
