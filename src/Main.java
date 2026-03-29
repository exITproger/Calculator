import java.util.Scanner;

public class Main {
    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        Main app = new Main();
        app.run();
        in.close();
    }

    private void run() {
        showAvailableSystems();
        while (true) {
            NumberSystem system = selectSystem();
            if (system == null) {
                System.out.println("Выход из программы.");
                break;
            }
            processCalculations(system);
        }
    }
    private void processCalculations(NumberSystem system) {
        while (true) {
            try {
                long result = calculate(system);
                printResult(result, system.getBase());
                System.out.print("\nПродолжить в текущей системе? (да/нет): ");
                String answer = in.nextLine().trim().toLowerCase();
                if (!answer.equals("да") && !answer.equals("yes") && !answer.equals("y")) {
                    break;
                }
            } catch (CalculatorException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    private void showAvailableSystems() {
        System.out.println("Доступные системы счисления:");
        System.out.println("\tHEX — шестнадцатеричная (0–9, A–F)");
        System.out.println("\tDEC — десятичная (0–9)");
        System.out.println("\tOCT — восьмеричная (0–7)");
        System.out.println("\tBIN — двоичная (0–1)");
    }

    private NumberSystem selectSystem() {
        while (true) {
            System.out.print("\nВведите систему счисления (или EXIT для выхода): ");
            String input = in.nextLine().toUpperCase().trim();
            if (input.equals("EXIT")) {
                return null;
            }
            try {
                return NumberSystem.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Система не распознана!");
            }
        }
    }

    private long calculate(NumberSystem system) {
        while (true) {
            System.out.print("Введите выражение: ");
            String input = in.nextLine().toUpperCase();

            try {
                Expression exp = parseExpression(input, system);
                ICalculatorInterface calc = new UniversalCalculator(system.getBase());
                return evaluate(exp, calc);
            } catch (CalculatorException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private long evaluate(Expression exp, ICalculatorInterface calc) {
        return switch (exp.getOperator()) {
            case '+' -> calc.add(exp.getLeft(), exp.getRight());
            case '-' -> calc.subtract(exp.getLeft(), exp.getRight());
            case '*' -> calc.multiply(exp.getLeft(), exp.getRight());
            case '/' -> calc.divide(exp.getLeft(), exp.getRight());
            case '%' -> calc.mod(exp.getLeft(), exp.getRight());
            default -> throw new CalculatorException("Неизвестная операция: " + exp.getOperator());
        };
    }

    private Expression parseExpression(String str, NumberSystem system) {
        String[] parts = str.trim().split("\\s+");

        if (parts.length != 3) {
            throw new CalculatorException("Неверный формат выражения. Используйте: число оператор число");
        }
        if (!isValidNumber(parts[0], system)) {
            throw new CalculatorException("Некорректный левый операнд");
        }
        if (!isValidNumber(parts[2], system)) {
            throw new CalculatorException("Некорректный правый операнд");
        }
        if (!parts[1].matches("[+\\-*/%]")) {
            throw new CalculatorException("Недопустимая операция. Разрешены: + - * / %");
        }

        return new Expression(parts[0], parts[1].charAt(0), parts[2]);
    }

    private boolean isValidNumber(String number, NumberSystem system) {
        if (number == null || number.trim().isEmpty()) return false;

        String num = number.toUpperCase();
        if (num.startsWith("+") || num.startsWith("-")) {
            num = num.substring(1);
            if (num.isEmpty()) return false;
        }

        String validChars = getValidChars(system);
        for (char c : num.toCharArray()) {
            if (validChars.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    private String getValidChars(NumberSystem system) {
        return switch (system) {
            case BIN -> "01";
            case OCT -> "01234567";
            case DEC -> "0123456789";
            case HEX -> "0123456789ABCDEF";
        };
    }

    private void printResult(long result, int base) {
        System.out.println("Результат:");
        printInBase(result, base);
        if (base != 16) printInBase(result, 16);
        if (base != 10) printInBase(result, 10);
        if (base != 8) printInBase(result, 8);
        if (base != 2) printInBase(result, 2);
    }

    private void printInBase(long result, int base) {
        switch (base) {
            case 16 -> System.out.println("\tHEX: " + Calculator.convertToBase(result, 16));
            case 10 -> System.out.println("\tDEC: " + result);
            case 8 -> System.out.println("\tOCT: " + Calculator.convertToBase(result, 8));
            case 2 -> System.out.println("\tBIN: " + Calculator.convertToBase(result, 2));
        }
    }
}