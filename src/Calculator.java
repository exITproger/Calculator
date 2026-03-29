public class Calculator {

    private Calculator() {}

    public static long add(long a, long b) {
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new CalculatorException("Переполнение при сложении: " + a + " + " + b);
        }
    }

    public static long subtract(long a, long b) {
        try {
            return Math.subtractExact(a, b);
        } catch (ArithmeticException e) {
            throw new CalculatorException("Переполнение при вычитании: " + a + " - " + b);
        }
    }

    public static long multiply(long a, long b) {
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
            throw new CalculatorException("Переполнение при умножении: " + a + " * " + b);
        }
    }

    public static long divide(long a, long b) {
        if (b == 0) throw new CalculatorException("Деление на ноль невозможно!");
        return (a % b == 0) ? (a / b) : 0;
    }

    public static long mod(long a, long b) {
        if (b == 0) throw new CalculatorException("Деление на ноль невозможно!");
        return a % b;
    }

    public static String convertToBase(long number, int base) {
        if (base != 2 && base != 8 && base != 10 && base != 16) {
            throw new CalculatorException("Недопустимая система счисления: " + base);
        }
        return Long.toString(number, base).toUpperCase();
    }

    public static long convertToInt(String number, int base) {
        if (base != 2 && base != 8 && base != 10 && base != 16) {
            throw new CalculatorException("Недопустимая система счисления: " + base);
        }
        if (number == null || number.trim().isEmpty()) {
            throw new CalculatorException("Число не может быть пустым");
        }
        try {
            return Long.parseLong(number, base);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Некорректное число или переполнение: " + number);
        }
    }
}