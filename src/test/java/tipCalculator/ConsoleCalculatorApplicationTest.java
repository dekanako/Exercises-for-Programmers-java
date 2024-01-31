package tipCalculator;

import io.dekanako.tipCalculator.Calculator;
import io.dekanako.tipCalculator.ConsoleCalculatorApplication;
import io.dekanako.tipCalculator.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleCalculatorApplicationTest {
    private final ConsoleOutputCapturer capturer = new ConsoleOutputCapturer();
    private final CalculatorSpy spy = new CalculatorSpy();

    @BeforeEach
    void setUp() {
        captureOutputAndMimicInput();
    }

    private void captureOutputAndMimicInput() {
        System.setOut(capturer.writer);
        mimicUserInput("32 33");
    }

    @Test
    void askUserForInput() {
        var application = new ConsoleCalculatorApplication(new CalculatorSpy());

        Scanner captured = new Scanner(capturer.captured());
        assertEquals("Enter Bill Amount", captured.nextLine());;
        assertEquals("Enter Tip rate", captured.nextLine());
    }
    @Test
    void afterAllInputsInputtedThenPassItToCalculator() {
        mimicUserInput("100.25 25");

        var application = new ConsoleCalculatorApplication(spy);

        assertEquals(spy.capturedBillAmount, 100.25);
        assertEquals(spy.capturedTipRate, 25);
    }


    @Test
    void afterCalculationDoneThenTotalAndTipAmountAreShown() {
        mimicUserInput("100 25");

        var application = new ConsoleCalculatorApplication(spy);

        Scanner actual = new Scanner(capturer.captured());
        actual.nextLine();
        actual.nextLine();
        assertEquals("Total amount is: 100.0$", actual.nextLine());;
        assertEquals("Tip Amount is 30.0$", actual.nextLine());
    }

    @Test
    void promptTillCorrectBillAmountIsEntered() {
        mimicUserInput("ds dd fd 32 33");

        var application = new ConsoleCalculatorApplication(spy);

        Scanner actual = new Scanner(capturer.captured());
        actual.nextLine();
        assertEquals(actual.nextLine(), "not good input");
        assertEquals(actual.nextLine(), "Enter Bill Amount");
        assertEquals(actual.nextLine(), "not good input");
        assertEquals(actual.nextLine(), "Enter Bill Amount");
        assertEquals(actual.nextLine(), "not good input");
        assertEquals(actual.nextLine(), "Enter Bill Amount");
    }

    @Test
    void promptTillCorrectRateIsEntered() {
        mimicUserInput("32 ds dd fd 32");

        var application = new ConsoleCalculatorApplication(spy);

        Scanner actual = new Scanner(capturer.captured());
        actual.nextLine();
        assertEquals(actual.nextLine(), "Enter Tip rate");
        assertEquals(actual.nextLine(), "not good input");
        assertEquals(actual.nextLine(), "Enter Tip rate");
        assertEquals(actual.nextLine(), "not good input");
        assertEquals(actual.nextLine(), "Enter Tip rate");
        assertEquals(actual.nextLine(), "not good input");
    }

    private static class ConsoleMimickerInputStream extends InputStream {
        private final StringReader reader;
        public ConsoleMimickerInputStream(String consoleInput) {
            reader = new StringReader(consoleInput);
        }
        @Override
        public int read() throws IOException {
            return reader.read();
        }
    }

    private static class ConsoleOutputCapturer {
        private final ByteArrayOutputStream container = new ByteArrayOutputStream();
        final PrintStream writer = new PrintStream(container);

        String captured() {
            return container.toString(Charset.defaultCharset());
        }

    }
    private static class CalculatorSpy implements Calculator {
        Double capturedBillAmount = 0.0;
        Double capturedTipRate = 0.0;
        @Override
        public Expense calculate(double billAmount, double tipRate) {
            capturedBillAmount = billAmount;
            capturedTipRate = tipRate;
            return new Expense(100, 30);
        }
    }

    private static void mimicUserInput(String input) {
        System.setIn(new ConsoleMimickerInputStream(input));
    }
}
