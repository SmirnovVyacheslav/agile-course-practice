package ru.unn.agile.LeftistHeap.infrastructure;

import ru.unn.agile.LeftistHeap.viewmodel.ILeftistHeapLogger;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LeftistHeapTxtLogger implements ILeftistHeapLogger {
    private final String logFileName;
    private BufferedWriter logWriter;

    public LeftistHeapTxtLogger(final String logFileName) {
        this.logFileName = logFileName;

        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(final String message) {
        try {
            logWriter.write("<" + getCurrentDateTime() + "> " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        List<String> logContent = new ArrayList<>();

        try {
            logContent = Files.readAllLines(Paths.get(logFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logContent;
    }

    @Override
    public String getMessage(final int messageIndex) {
        return getLog().get(messageIndex);
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
