package ru.unn.agile.Polinom.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PolinomViewModelWithTxtLoggerTests {
    public void setExternalPolinomlViewModel(final PolinomViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new PolinomViewModel(new FakePolinomLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetNullLogger() {
        new PolinomViewModel(null);
    }

    @Test
    public void createdLogIsEmptyByDefault() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperOperationTypeIfAddButtonPressed() {
        setInputData();
        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).matches(".*" + PolinomViewModel.Operation.ADD + ".*"));
    }

    @Test
    public void logContainsProperOperationTypeIfSubstractButtonPressed() {
        setInputData();
        viewModel.operation(PolinomViewModel.Operation.SUBSTRACT);

        assertTrue(viewModel.getLog().get(0).
            matches(".*" + PolinomViewModel.Operation.SUBSTRACT + ".*"));
    }

    @Test
    public void logContainsProperOperationTypeIfMultiplyButtonPressed() {
        setInputData();
        viewModel.operation(PolinomViewModel.Operation.MULTIPLY);

        assertTrue(viewModel.getLog().get(0).
            matches(".*" + PolinomViewModel.Operation.MULTIPLY + ".*"));
    }

    @Test
    public void logContainsProperOperationTypeIfDivideButtonPressed() {
        setInputData();
        viewModel.operation(PolinomViewModel.Operation.DIVIDE);

        assertTrue(viewModel.getLog().get(0).
            matches(".*" + PolinomViewModel.Operation.DIVIDE + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setInputData();

        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).matches(".*" + viewModel.firstOperandProperty().get().
            replace(".", "\\.").replace("+", "\\+").replace("^", "\\^")
            + ".*" + viewModel.secondOperandProperty().get().
            replace(".", "\\.").replace("+", "\\+").replace("^", "\\^") + ".*"));
    }

    @Test
    public void argumentsInLogAreProperlyFormatted() {
        setInputData();

        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).matches(".*Arguments: \\["
            + escapingOperandSpecialCharacters(viewModel.firstOperandProperty().get()) + "\\]; \\["
            + escapingOperandSpecialCharacters(viewModel.secondOperandProperty().get()) + "\\].*"));
    }

    @Test
    public void logContainsCorrectResultMessageIfCulculationIsCorrect() {
        setInputData();

        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).matches(".*Result: Success.*"));
    }

    @Test
    public void logContainsCorrectResultMessageIfSomeFieldIsEmpty() {
        viewModel.firstOperandProperty().set("1");

        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).
            matches(".*Result: " + PolinomViewModel.Errors.EMPTY_FIELD.getMessage() + ".*"));
    }

    @Test
    public void logContainsCorrectResultMessageIfInputIsIncorrect() {
        viewModel.firstOperandProperty().set("1 *x^2");
        viewModel.secondOperandProperty().set("x ^1");

        viewModel.operation(PolinomViewModel.Operation.ADD);

        assertTrue(viewModel.getLog().get(0).
            matches(".*Result: " + PolinomViewModel.Errors.BAD_FORMAT.getMessage() + ".*"));
    }

    @Test
    public void logContainsCorrectResultMessageIfDivideByZero() {
        viewModel.firstOperandProperty().set("2");
        viewModel.secondOperandProperty().set("0");

        viewModel.operation(PolinomViewModel.Operation.DIVIDE);

        assertTrue(viewModel.getLog().get(0).
            matches(".*Result: " + PolinomViewModel.Errors.DIVIDE_BY_ZERO.getMessage() + ".*"));
    }

    @Test
    public void logContainsCorrectResultMessageIfDivideByLargerDegree() {
        viewModel.firstOperandProperty().set("1");
        viewModel.secondOperandProperty().set("1x^1");

        viewModel.operation(PolinomViewModel.Operation.DIVIDE);

        assertTrue(viewModel.getLog().get(0).
            matches(".*Result: " + PolinomViewModel.Errors.DIVIDE_BY_LARGE_DEGREE.getMessage()
            + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setInputData();

        viewModel.operation(PolinomViewModel.Operation.ADD);
        viewModel.operation(PolinomViewModel.Operation.MULTIPLY);
        viewModel.operation(PolinomViewModel.Operation.SUBSTRACT);

        assertEquals(3, viewModel.getLog().size());
    }

    private void setInputData() {
        viewModel.firstOperandProperty().set("1 + 2x^2");
        viewModel.secondOperandProperty().set("2 - 3x^1");
    }

    private String escapingOperandSpecialCharacters(final String operand) {
        return operand.replace(".", "\\.").replace("+", "\\+").replace("^", "\\^");
    }

    private PolinomViewModel viewModel;
}
