package io.dekanako.tipCalculator;

public class TipCalculator implements Calculator {
    public Expense calculate(double billAmount, double tipRate) {
        double tipAmount = calculateTipAmount(billAmount, tipRate);
        return new Expense(tipAmount + billAmount, tipAmount);
    }

    private double calculateTipAmount(double billAmount, double tipRate) {
        return billAmount * (tipRate / 100);
    }
}
