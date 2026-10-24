class TwoConstructorInvocations {
    TwoConstructorInvocations(int x) {}

    TwoConstructorInvocations() {
        this(1);
        this(2);
    }
}
