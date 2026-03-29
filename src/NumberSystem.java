public enum NumberSystem {
    BIN(2),
    OCT(8),
    DEC(10),
    HEX(16);

    private final int base;

    NumberSystem(int base) {
        this.base = base;
    }

    public int getBase() {
        return base;
    }
}