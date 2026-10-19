// Java 16: pattern matching for instanceof binds a variable when the test succeeds.
import java.util.List;
import java.util.Map;

class InstanceOfPatterns {
    int length(Object o) {
        if (o instanceof String s) {
            return s.length();
        }
        if (o instanceof final CharSequence cs && cs.length() > 0) {
            return cs.length();
        }
        if (!(o instanceof Integer i)) {
            return 0;
        }
        return i;
    }

    boolean generic(Object o) {
        return o instanceof List<?> list && !list.isEmpty()
            || o instanceof Map.Entry<?, ?> entry && entry.getKey() != null;
    }

    // A type argument after instanceof belongs to the pattern's type, not to a comparison
    boolean typeArguments(Iterable<String> it) {
        return it instanceof List<String> l && l.size() > 0;
    }

    boolean arrays(Object o) {
        return o instanceof int[] ints && ints.length > 0 || o instanceof String[][] grid;
    }

    boolean annotated(Object o) {
        return o instanceof @Deprecated String s && s.isEmpty();
    }

    String conditional(Object o) {
        return o instanceof Number n ? n.toString() : "none";
    }

    boolean plainTypes(Object o) {
        return o instanceof String && o instanceof java.io.Serializable && o instanceof List<?>;
    }
}
