package ru.unn.agile.TemperatureConverter.infrastructure;

import ru.unn.agile.TemperatureConverter.viewmodel.TemperatureConverterLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TemperatureConverterTxtLogger implements TemperatureConverterLogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final String fileOfLog;
    private final BufferedWriter writerOfLog;

    public TemperatureConverterTxtLogger(final String fileOfLog) {
        this.fileOfLog = fileOfLog;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileOfLog));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        writerOfLog = logWriter;
    }

    @Override
    public void log(final String logMessage) {
        try {
            writerOfLog.write(currentTime() + " > " + logMessage);
            writerOfLog.newLine();
            writerOfLog.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader readerOfLog;

        ArrayList<String> log = new ArrayList<String>();

        try {
            readerOfLog = new BufferedReader(new FileReader(fileOfLog));
            String line = readerOfLog.readLine();

            while (line != null) {
                log.add(line);
                line = readerOfLog.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }

    private static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return dataFormat.format(calendar.getTime());
    }
}
