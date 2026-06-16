class Expressions {
    void test() {
        // Arithmetic
        a = x + y;
        a = x - y;
        a = x * y;
        a = x / y;
        a = x % y;

        // Precedence
        a = 1 + 2 * 3;
        a = (1 + 2) * 3;
        a = x + y * x - y / x;

        // Unary
        a = -x;
        a = +x;
        a = !b;
        a = ~x;

        // Prefix / postfix as bare statement expressions
        ++x;
        --x;
        x++;
        x--;

        // Shifts
        a = x << 2;
        a = x >> 2;
        a = x >>> 2;

        // Relational
        a = x < y;
        a = x > y;
        a = x <= y;
        a = x >= y;

        // Equality
        a = x == y;
        a = x != y;

        // Bitwise
        a = x & y;
        a = x | y;
        a = x ^ y;

        // Logical short-circuit
        a = b && b;
        a = b || b;

        // Conditional
        a = x > y ? x : y;
        a = x > 0 ? 1 : x < 0 ? -1 : 0;

        // instanceof
        a = o instanceof String;

        // Casts
        a = (int) 1.5;
        a = (String) o;
        a = (Comparable<String> & java.io.Serializable) o;

        // Compound assignment
        x += 1;
        x -= 1;
        x *= 2;
        x /= 2;
        x %= 2;
        x &= 0xFF;
        x |= 0xFF;
        x ^= 0xFF;
        x <<= 1;
        x >>= 1;
        x >>>= 1;

        // Field access
        a = arr.length;
        a = o.toString();

        // Array access
        a = arr[0];
        a = mat[0][1];

        // Method invocation
        a = "hello".trim();
        a = "abc".substring(0, 2);

        // Chained access
        a = "hello".trim().toUpperCase().length();

        // Object creation
        a = new Object();
        a = new java.util.ArrayList<String>();
        a = new java.util.ArrayList<>();

        // Array creation
        a = new int[10];
        a = new int[10][20];
        a = new int[10][];

        // this, super, class literals
        a = this;
        a = super.toString();
        a = String.class;
        a = int.class;
        a = void.class;

        // Parenthesized
        a = (((x)));

        // Lambdas (J8)
        a = () -> {};
        a = i -> i + 1;
        a = (i, j) -> i + j;

        // Method references (J8)
        a = String::length;
        a = Object::new;
    }
}
