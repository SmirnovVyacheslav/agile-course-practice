package ru.unn.agile.TemperatureConverter.viewmodel;

import ru.unn.agile.TemperatureConverter.model.TemperatureConverter;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;

import java.util.List;

public class ViewModel {
    private String inputTemperature;
    private String resultTemperature;
    private Status status;
    private TemperatureScaleName scale;
    private boolean isConvertButtonEnable;
    private final TemperatureConverterLogger logger;
    private boolean isInputChanged;

    public ViewModel(final TemperatureConverterLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;

        setStateToDefault();
    }

    private void setStateToDefault() {
        inputTemperature = "";
        resultTemperature = "";
        status = Status.WAITING;
        scale = TemperatureScaleName.FAHRENHEIT;
        isConvertButtonEnable = false;
        isInputChanged = false;
    }

    public String getInputTemperature() {
        return inputTemperature;
    }

    public String getResultTemperature() {
        return resultTemperature;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.toString();
    }

    public TemperatureScaleName getScale() {
        return scale;
    }

    public boolean isConvertButtonEnabled() {
        return isConvertButtonEnable;
    }

    public void setInputTemperature(final String inputTemperature) {
        this.inputTemperature = inputTemperature;
        isInputChanged = true;
    }

    public void setScale(final TemperatureScaleName scale) {
        if (this.scale != scale) {
            this.scale = scale;
            logger.log(LogMessage.SCALE_CHANGED.toString() + scale.toString());
        }
    }

    public boolean parse() {
        if (inputTemperature.isEmpty()) {
            status = Status.WAITING;
            isConvertButtonEnable = false;
        } else {
            double parsedInputTemperature;
            try {
                parsedInputTemperature = Double.parseDouble(inputTemperature);
                Double.parseDouble(inputTemperature);
                status = Status.READY;
                resultTemperature = "";
                isConvertButtonEnable = true;
            } catch (NumberFormatException exception) {
                status = Status.BAD_FORMAT;
                isConvertButtonEnable = false;
                resultTemperature = "";
                return isConvertButtonEnable;
            }

            if (parsedInputTemperature < TemperatureConverter.PHYSICAL_LIMIT) {
                status = Status.NON_PHYSICAL_VALUE;
                isConvertButtonEnable = false;
            } else {
                status = Status.READY;
                resultTemperature = "";
                isConvertButtonEnable = true;
            }
        }

        return isConvertButtonEnable;
    }

    public void convert() {
        logger.log(createConvertLogMessage());
        if (parse()) {
            TemperatureConverter converter = new TemperatureConverter(scale);
            double temperature = converter.convert(Double.parseDouble(inputTemperature));
            resultTemperature = Double.toString(temperature);
            status = Status.SUCCESS;
        }
    }

    private String createConvertLogMessage() {
        String message =
                LogMessage.CONVERT_PRESSED.toString()
                        + LogMessage.INPUT_VALUE.toString()
                        + inputTemperature
                        + LogMessage.TARGET_SCALE.toString()
                        + scale.toString();
        return message;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public void onInputValueFocusLost() {
        if (isInputChanged) {

            parse();

            if (status == Status.BAD_FORMAT) {
                logger.log(LogMessage.INCORRECT_INPUT.toString() + inputTemperature);
            } else if (status == Status.NON_PHYSICAL_VALUE) {
                logger.log(LogMessage.NON_PHYSICAL_INPUT.toString() + inputTemperature);
            } else {
                logger.log(LogMessage.INPUT_EDITED.toString() + inputTemperature);
            }

            isInputChanged = false;
        }
    }


}
