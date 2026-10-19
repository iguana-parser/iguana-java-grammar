// Java 16: records, enums, and interfaces may be declared locally in a block.
class LocalTypeDeclarations {
    void m() {
        enum Color { RED, GREEN }
        interface Greeter {
            String greet(String name);
        }
        record Pair(int a, int b) {}
        class Impl implements Greeter {
            public String greet(String name) {
                return name;
            }
        }
        Greeter g = new Impl();
        Color c = Color.RED;
    }
}
