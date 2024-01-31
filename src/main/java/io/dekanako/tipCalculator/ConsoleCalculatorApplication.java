package io.dekanako.tipCalculator;

import java.util.Scanner;

public class ConsoleCalculatorApplication {
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleCalculatorApplication(Calculator calculator) {
        var bill = promptForInput("Enter Bill Amount");
        var tip = promptForInput("Enter Tip rate");

        var expense = calculator.calculate(bill, tip);

        System.out.printf("Total amount is: %s$%n", expense.total);
        System.out.printf("Tip Amount is %s$%n", expense.tipAmount);
    }

    private double promptForInput(String promptMessage) {
        var input = 0d;
        while (input <= 0) {
            System.out.println(promptMessage);

            if (scanner.hasNextDouble())
                input = scanner.nextDouble();
            else {
                System.out.println("not good input");
                scanner.next();
            }
        }
        return input;
    }
}
