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

    // Qualified catch type
    void catchQualified() {
        try {
            simple();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // try-with-resources
    void twr() throws Exception {
        try (java.io.InputStream in = null) {
            in.read();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            System.out.println("done");
        }
    }
}
