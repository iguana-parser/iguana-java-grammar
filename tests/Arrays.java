class Arrays {
    // Field declarations - modern style
    int[] a;
    int[][] b;
    String[] c;

    // Legacy C-style declarations
    int d[];
    int e[][];

    // Mixed legacy and modern
    int[] f[];

    // Initialized
    int[] g = {1, 2, 3};
    int[][] h = {{1, 2}, {3, 4}};
    int[] empty = {};
    int[] trailingComma = {1, 2, 3,};

    void test() {
        // Local array declarations
        int[] local;
        int[][] localMulti;

        // Array creation - sized
        int[] sized = new int[10];
        int[][] sized2 = new int[10][20];
        int[][] sized3 = new int[10][];

        // Array creation - initialized
        int[] init = new int[]{1, 2, 3};
        int[][] init2 = new int[][]{{1}, {2, 3}};

        // Array of reference type
        String[] strs = new String[5];
        String[] strInit = new String[]{"a", "b", "c"};

        // Array access
        int v = a[0];
        int w = b[0][1];

        // Array length
        int len = a.length;

        // Array as method return / parameter / cast
        Object o = a;
        int[] cast = (int[]) o;
    }

    // Array as return type
    int[] returnsArr() { return null; }

    // Array as parameter
    void takesArr(int[] arr) {}
    void takesArrLegacy(int arr[]) {}
    void takesMulti(int[][] arr) {}

    // Trailing array dimension after method declarator (legacy)
    int returnsArrLegacy() [] { return null; }

    // Varargs
    void varargs(int... xs) {}
    void varargsMixed(String prefix, int... xs) {}
    void varargsArr(int[]... xss) {}
}
