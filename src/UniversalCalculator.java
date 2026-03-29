public class UniversalCalculator implements ICalculatorInterface {
    private final int base;

    public UniversalCalculator(int base) {
        this.base = base;
    }

    @Override
    public long add(String a, String b) {
        return Calculator.add(Calculator.convertToInt(a, base), Calculator.convertToInt(b, base));
    }

    @Override
    public long subtract(String a, String b) {
        return Calculator.subtract(Calculator.convertToInt(a, base), Calculator.convertToInt(b, base));
    }

    @Override
    public long multiply(String a, String b) {
        return Calculator.multiply(Calculator.convertToInt(a, base), Calculator.convertToInt(b, base));
    }

    @Override
    public long divide(String a, String b) {
        return Calculator.divide(Calculator.convertToInt(a, base), Calculator.convertToInt(b, base));
    }

    @Override
    public long mod(String a, String b) {
        return Calculator.mod(Calculator.convertToInt(a, base), Calculator.convertToInt(b, base));
    }
}