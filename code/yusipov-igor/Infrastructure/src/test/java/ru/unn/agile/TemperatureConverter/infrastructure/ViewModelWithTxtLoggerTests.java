package ru.unn.agile.TemperatureConverter.infrastructure;

import ru.unn.agile.TemperatureConverter.viewmodel.ViewModel;
import ru.unn.agile.TemperatureConverter.viewmodel.ViewModelLoggingTests;

public class ViewModelWithTxtLoggerTests extends ViewModelLoggingTests {
    @Override
    public void setUp() {
        TemperatureConverterTxtLogger trueLogger =
                new TemperatureConverterTxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(trueLogger));
    }
}
