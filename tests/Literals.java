class Literals {
    // Integer — decimal
    int dec0 = 0;
    int dec1 = 42;
    int dec2 = 1_000_000;
    long decL = 42L;
    long decLLower = 42l;

    // Integer — hex
    int hex = 0x1F;
    int hexUpper = 0XCAFE_BABE;
    long hexL = 0xFFL;

    // Integer — octal
    int oct = 0777;
    int oct0 = 0;
    long octL = 0777L;

    // Integer — binary (Java 7)
    int bin = 0b1010;
    int binSep = 0b1010_1100;
    long binL = 0b11111111L;

    // Float — decimal
    float f0 = 1.0f;
    float f1 = 1.0F;
    double d0 = 1.0;
    double d1 = 1.0d;
    double d2 = 1.0D;
    double d3 = .5;
    double d4 = 1.;
    double d5 = 1e10;
    double d6 = 1.5e-3;
    double d7 = 1.5E+10;

    // Float — hex
    double hf0 = 0x1.8p1;
    double hf1 = 0x1p10;
    double hf2 = 0x1.0p0;

    // Boolean
    boolean t = true;
    boolean f = false;

    // Character
    char cPlain = 'a';
    char cNewline = '\n';
    char cTab = '\t';
    char cBackslash = '\\';
    char cSingleQuote = '\'';
    char cDoubleQuote = '\"';
    char cReturn = '\r';
    char cBackspace = '\b';
    char cFormFeed = '\f';
    char cNull = '\0';
    char cOctal = '\101';
    char cUnicode = '\u0041';        // unicode escape (single u)
    char cUnicodeUUU = '\uuuu0041';      // multiple u's allowed per JLS

    // String
    String s0 = "";
    String s1 = "hello";
    String s2 = "with \"quotes\"";
    String s3 = "tab\there";
    String s4 = "newline\nhere";
    String s5 = "unicode \u0041 in string";
    String s6 = "backslash \\";

    // Null
    Object n = null;

    // Negative literals (parsed as unary minus + literal)
    int neg = -42;
    double negF = -1.5;
}
