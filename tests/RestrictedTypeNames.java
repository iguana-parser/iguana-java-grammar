// A restricted identifier may qualify a type name, since the qualifier is a package
// name. Only the final component of the type name is a TypeIdentifier, and a component
// with annotations or type arguments is a type component rather than a package one.
import yield.Foo;
import yield.*;
import a.yield.*;
import static A.yield;
import static a.b.C.yield;

class RestrictedTypeNames {
    yield.Foo a;
    var.Foo b;
    a.yield.Foo c;
    a.var.b.Foo d;
    yield.Foo<String> e;
    java.util.List<yield.Foo> f;

    @yield.Ann
    void annotated() {}

    void creation() {
        Object o = new yield.Foo();
        Object p = new a.var.Foo<String>();
    }

    void qualifiedThis() {
        Object o = RestrictedTypeNames.this;
        Object p = a.b.this;
    }

    yield.Foo returns() {
        return null;
    }

    void params(yield.Foo p, var.Foo q) {}

    void methodRefs() {
        Object a = yield.C<String>::m;
        Object b = a.b.C<String>::m;
        Object c = @A C<String>::m;
        Object d = @A C::m;
        Object e = RestrictedTypeNames.super::toString;
    }
}
