// Top-level classes covering modifier and inheritance variations.

class Plain {}

public class Classes {
    int field;
}

abstract class Abstract {
    abstract void doit();
}

final class Fin {}

strictfp class Strict {}

abstract final class IllegalButParses {}  // semantic error; grammar accepts

class Generic<T> { T value; }

class WithExtends extends Object {}

class WithExtendsGeneric extends java.util.ArrayList<String> {}

class WithImplements implements Runnable {
    public void run() {}
}

class WithMultipleImplements implements Runnable, java.io.Serializable, Cloneable {
    public void run() {}
}

class WithExtendsAndImplements extends Object implements Runnable, Cloneable {
    public void run() {}
}

class BoundedGeneric<T extends Number & Comparable<T>> extends java.util.AbstractList<T> {
    public T get(int i) { return null; }
    public int size() { return 0; }
}

class WithFields {
    int plain;
    final int fin = 1;
    static int stat;
    static final int constant = 42;
    private int priv;
    protected int prot;
    public int pub;
    transient int trans;
    volatile int vol;
    int multi1, multi2, multi3;
    int initialized = 1;
    int[] arr;
    int[] arrInit = {1, 2, 3};
}

class WithInitializers {
    static int s;
    int i;

    static { s = 1; }   // static initializer
    { i = 1; }          // instance initializer
}
