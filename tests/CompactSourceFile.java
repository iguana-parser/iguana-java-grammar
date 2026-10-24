// Java 25: a compact source file declares methods and fields at the top level.
import module java.base;
import java.util.List;

final List<String> names = List.of("a", "b");

record Pair(String first, String second) {}

enum Mode { QUIET, LOUD }

;

void main() {
    for (String name : names) {
        IO.println(greeting(name));
    }
}

static String greeting(String name) {
    return "Hello, " + name;
}

interface Greeter {
    String greet(String name);
}

int count;
;
