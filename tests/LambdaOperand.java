class LambdaOperand {
    boolean test(boolean a) {
        return a || x -> x;
    }
}
