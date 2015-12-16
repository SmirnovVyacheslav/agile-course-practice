package ru.unn.agile.TemperatureConverter.model;

public class TemperatureConverter {

    private final double base;
    private final double scaling;

    public static final double PHYSICAL_LIMIT = -273.15;

    public TemperatureConverter(final TemperatureScaleName scaleName) {
        base = scaleName.getBase();
        scaling = scaleName.getScaling();
    }

    public double convert(final double temperatureInCelsius) {
        double temperatureInOtherScale = scaling * temperatureInCelsius + base;

        if (temperatureInOtherScale >= Double.MAX_VALUE || temperatureInCelsius < PHYSICAL_LIMIT) {
            throw new IllegalArgumentException();
        }

        return temperatureInOtherScale;
    }
}
