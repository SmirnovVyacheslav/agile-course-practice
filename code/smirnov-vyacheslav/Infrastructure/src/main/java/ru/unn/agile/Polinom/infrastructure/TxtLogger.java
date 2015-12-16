package ru.unn.agile.Polinom.infrastructure;

import ru.unn.agile.Polinom.viewmodel.IPolinomLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements IPolinomLogger {
    public TxtLogger(final String logFileName) {
        this.logFileName = logFileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String message) {
        try {
            writer.write(getCurrentDate() + " > " + message);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(logFileName));
            String logMessage = logReader.readLine();

            while (logMessage != null) {
                log.add(logMessage);
                logMessage = logReader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }

    private static String getCurrentDate() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return dateFormat.format(date.getTime());
    }

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String logFileName;
}
