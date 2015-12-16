package ru.unn.agile.TemperatureConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {

    private ViewModel viewModel;

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
    public void canSetAllDefaultValues() {
        assertEquals("", viewModel.getInputTemperature());
        assertEquals("", viewModel.getResultTemperature());
        assertEquals(Status.WAITING, viewModel.getStatus());
        assertEquals(TemperatureScaleName.FAHRENHEIT, viewModel.getScale());
        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void isStatusWaitingAtStart() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canSetInputTemperature() {
        final String value = "0.0";
        viewModel.setInputTemperature(value);
        assertEquals("0.0", viewModel.getInputTemperature());
    }

    @Test
    public void canSetScale() {
        viewModel.setScale(TemperatureScaleName.NEWTON);
        assertEquals(viewModel.getScale(), TemperatureScaleName.NEWTON);
    }

    @Test
    public void canSetStatusReadyWhenInputIsCorrect() {
        final String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(viewModel.getStatus(), Status.READY);
    }

    @Test
    public void canSetStatusBadFormatWhenInputIsIncorrect() {
        final String value = "SomethingWicked";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(viewModel.getStatus(), Status.BAD_FORMAT);
    }

    @Test
    public void canSetStatusWaitingWhenInputIsEmpty() {
        final String value = "";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(viewModel.getStatus(), Status.WAITING);
    }

    @Test
    public void isConvertButtonEnabledWhenInputIsCorrect() {
        final String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(true, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void isConvertButtonDisabledWhenInputIsIncorrect() {
        String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        value = "SomethingWicked";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void isConvertButtonDisabledWhenInputIsEmpty() {
        String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        value = "";
        viewModel.setInputTemperature(value);
        viewModel.parse();
        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetStatusSuccessWhenConvertingIsSuccess() {
        final String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.setScale(TemperatureScaleName.KELVIN);
        viewModel.convert();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void isCorrectStatusName() {
        final String value = "1.0";
        viewModel.setInputTemperature(value);
        viewModel.convert();
        assertEquals(viewModel.getStatusName(), "Success");
    }

    @Test
    public void isStatusNonPhysicalValueWhenTemperatureIsNonPhysical() {
        final String value = "-274.0";
        viewModel.setInputTemperature(value);
        viewModel.convert();
        assertEquals(Status.NON_PHYSICAL_VALUE, viewModel.getStatus());
    }
}
