class ContextualKeywords {
    // All ten module-related contextual keywords must remain valid identifiers
    int module = 1;
    int open = 2;
    int requires = 3;
    int exports = 4;
    int opens = 5;
    int uses = 6;
    int provides = 7;
    int to = 8;
    int with = 9;
    int transitive = 10;

    // Java 10: 'var' is contextual too — valid as an identifier outside type position
    int var = 11;

    // Java 16 and 17: 'record', 'sealed', and 'permits' are contextual as well
    int record = 12;
    int sealed = 13;
    int permits = 14;
    int non = 15;

    void use() {
        int sum = module + open + requires + exports + opens
                + uses + provides + to + with + transitive + var;
        System.out.println(sum);

        // Outside a class or interface declaration, non-sealed is a subtraction
        int difference = non-sealed + record - permits;
    }

    // Methods named after contextual keywords
    void module() {}
    void exports() {}
    void provides() {}
    void var() {}
    void record() {}
    void sealed() {}
    void permits() {}
}
