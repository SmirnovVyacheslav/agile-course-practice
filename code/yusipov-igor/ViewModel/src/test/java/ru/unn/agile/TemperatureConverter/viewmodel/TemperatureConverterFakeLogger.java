package ru.unn.agile.TemperatureConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class TemperatureConverterFakeLogger implements TemperatureConverterLogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void log(final String logMessage) {
        log.add(logMessage);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
