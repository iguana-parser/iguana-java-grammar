import java.util.spi.ToolProvider;

@Deprecated
open module com.example.foo {
    requires java.base;
    requires transitive java.sql;
    requires static org.example.optional;

    exports com.example.foo.api;
    exports com.example.foo.internal to com.example.bar, com.example.baz;

    opens com.example.foo.impl;
    opens com.example.foo.legacy to org.legacy.tool;

    uses com.example.spi.Service;

    provides com.example.spi.Service with com.example.foo.impl.ServiceImpl,
                                          com.example.foo.impl.AltImpl;
}
