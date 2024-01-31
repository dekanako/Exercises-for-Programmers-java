package tipCalculator;

import io.dekanako.tipCalculator.Expense;
import io.dekanako.tipCalculator.TipCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TipCalculatorTest {
    @Test
    void calculateExpense() {
        var calculator = new TipCalculator();

        Expense expense = calculator.calculate(10.0, 15);

        assertEquals(11.50, expense.total);
        assertEquals(1.50, expense.tipAmount);
    }

    @Test
    void totalShouldBeBillAmountPlusTipAmount() {
        var calculator = new TipCalculator();

        Expense expense = calculator.calculate(100.0, 25);

        assertEquals(125.0, expense.total);
        assertEquals(25.0, expense.tipAmount);
    }

}
