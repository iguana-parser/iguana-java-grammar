// Java 25: a module import imports the public top-level types of the packages that a
// module exports.
import module java.base;
import module java.sql;
import module.Foo;
import module.util.*;

class ModuleImports {
    List<String> names = new ArrayList<>();
}
