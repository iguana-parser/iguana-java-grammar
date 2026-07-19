import java.io.IOException;
import java.sql.SQLException;

class ThrowsAndCatch {
    // Single throws
    void simple() throws IOException {}

    // Multiple throws
    void multi() throws IOException, SQLException, RuntimeException {}

    // Qualified throws
    void qualified() throws java.io.IOException, java.sql.SQLException {}

    // Catch with single type
    void catchSingle() {
        try {
            simple();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Multi-catch (Java 7)
    void catchMulti() {
        try {
            multi();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Annotated multi-catch: because the first catch type is UnannClassType, the leading
    // annotation can only bind as a VariableModifier, not to the type. Regression guard for
    // the CatchType annotation-attachment ambiguity.
    void catchAnnotated() {
        try {
            multi();
        } catch (@Deprecated IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Qualified catch type
    void catchQualified() {
        try {
            simple();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // try-with-resources, declaration form (Java 7+)
    void twr() throws Exception {
        try (java.io.InputStream in = null) {
            in.read();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            System.out.println("done");
        }
    }

    // try-with-resources, variable-access form: effectively-final local (Java 9+)
    void twrAccess() throws Exception {
        java.io.InputStream src = null;
        try (src) {
            src.read();
        }
    }

    // try-with-resources, variable-access form: static final field
    static final java.io.InputStream SHARED = null;

    void twrField() throws Exception {
        try (SHARED) {
            SHARED.read();
        }
    }

    // try-with-resources, variable-access form: this.field
    java.io.InputStream fieldStream;

    void twrThisField() throws Exception {
        try (this.fieldStream) {
            this.fieldStream.read();
        }
    }

    // Multiple resources mixing declaration and access forms
    void twrMixed() throws Exception {
        java.io.InputStream pre = null;
        try (pre; java.io.InputStream fresh = null) {
            pre.read();
            fresh.read();
        }
    }
}
