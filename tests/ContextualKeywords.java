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

    void use() {
        int sum = module + open + requires + exports + opens
                + uses + provides + to + with + transitive;
        System.out.println(sum);
    }

    // Methods named after contextual keywords
    void module() {}
    void exports() {}
    void provides() {}
}
