public class Expression {
    private final String left;
    private final String right;
    private final char operator;

    public Expression(String left, char operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public char getOperator() {
        return operator;
    }
}