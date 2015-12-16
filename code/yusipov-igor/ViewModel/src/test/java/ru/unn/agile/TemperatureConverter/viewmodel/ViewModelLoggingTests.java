package ru.unn.agile.TemperatureConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ViewModelLoggingTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        TemperatureConverterFakeLogger logger = new TemperatureConverterFakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canCreateWithLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionWithNullLogger() {
        ViewModel viewModelWithNullLogger = new ViewModel(null);
    }

    @Test
    public void logIsEmptyAfterConstruction() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsMessageWhenInputIsChanged() {
        viewModel.setInputTemperature("0.0");
        viewModel.onInputValueFocusLost();

        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logContainsErrorMessageWhenNewInputHasBadFormat() {
        final String incorrectInput = "SomethingWicked";
        viewModel.setInputTemperature(incorrectInput);
        viewModel.onInputValueFocusLost();
        List<String> log = viewModel.getLog();
        final String expected = LogMessage.INCORRECT_INPUT.toString() + incorrectInput;
        assertThat(log.get(0), containsString(expected));
    }

    @Test
    public void logContainsNormalMessageWhenNewInputIsCorrect() {
        final String correctInput = "0.0";
        viewModel.setInputTemperature(correctInput);
        viewModel.onInputValueFocusLost();
        List<String> log = viewModel.getLog();
        final String expected = LogMessage.INPUT_EDITED.toString() + correctInput;
        assertThat(log.get(0), containsString(expected));
    }

    @Test
    public void logContainsErrorMessageWhenNewInputIsNonPhysical() {
        final String correctInput = "-300.0";
        viewModel.setInputTemperature(correctInput);
        viewModel.onInputValueFocusLost();
        List<String> log = viewModel.getLog();
        final String expected = LogMessage.NON_PHYSICAL_INPUT.toString() + correctInput;
        assertThat(log.get(0), containsString(expected));
    }

    @Test
    public void canAddMultipleMessagesInLogWhenInputIsSameWithLostFocus() {
        final String input = "0.0";
        viewModel.setInputTemperature(input);
        viewModel.onInputValueFocusLost();
        viewModel.setInputTemperature(input);
        viewModel.onInputValueFocusLost();
        List<String> log = viewModel.getLog();
        assertEquals(2, log.size());
    }

    @Test
    public void doNotAddMultipleMessagesInLogWhenFocusHoldOn() {
        final String input = "0.0";
        viewModel.setInputTemperature(input);
        viewModel.setInputTemperature(input);
        viewModel.onInputValueFocusLost();
        viewModel.onInputValueFocusLost();
        List<String> log = viewModel.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void canAddMessageToLogWhenScaleIsChanged() {
        viewModel.setScale(TemperatureScaleName.NEWTON);
        List<String> log = viewModel.getLog();
        final String expected = LogMessage.SCALE_CHANGED.toString()
                + TemperatureScaleName.NEWTON.toString();
        assertThat(log.get(0), containsString(expected));
    }

    @Test
    public void canAddMessageToLogWhenConvertIsPressed() {
        final String input = "0.0";
        viewModel.setInputTemperature(input);
        viewModel.onInputValueFocusLost();
        viewModel.convert();
        String message = viewModel.getLog().get(1);
        final String expected = LogMessage.CONVERT_PRESSED.toString()
                + LogMessage.INPUT_VALUE.toString()
                + input
                + LogMessage.TARGET_SCALE.toString()
                + viewModel.getScale().toString();
        assertThat(message, containsString(expected));
    }
}
