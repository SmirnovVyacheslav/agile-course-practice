package ru.unn.agile.TemperatureConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ViewModelConverterCorrectnessTest {


    private ViewModel viewModel;
    private String inputTemperature;
    private TemperatureScaleName scale;
    private String outputTemperature;

    public ViewModelConverterCorrectnessTest(final String inputTemperature,
                                             final TemperatureScaleName scale,
                                             final String outputTemperature) {
        this.inputTemperature = inputTemperature;
        this.scale = scale;
        this.outputTemperature = outputTemperature;
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


    @Parameterized.Parameters
    public static Collection converting() {
        return Arrays.asList(new Object[][]{
                {"0.0", TemperatureScaleName.FAHRENHEIT, "32.0"},
                {"1.0", TemperatureScaleName.FAHRENHEIT, "33.8"},
                {"-1.0", TemperatureScaleName.FAHRENHEIT, "30.2"},
                {"0.0", TemperatureScaleName.KELVIN, "273.0"},
                {"1.0", TemperatureScaleName.KELVIN, "274.0"},
                {"-1.0", TemperatureScaleName.KELVIN, "272.0"},
                {"0.0", TemperatureScaleName.NEWTON, "0.0"},
                {"1.0", TemperatureScaleName.NEWTON, "0.33"},
                {"-1.0", TemperatureScaleName.NEWTON, "-0.33"}
        });
    }

    @Test
    public void canConvertToOtherScale() {
        viewModel.setInputTemperature(inputTemperature);
        viewModel.setScale(scale);
        viewModel.convert();
        assertEquals(outputTemperature, viewModel.getResultTemperature());
    }
}
