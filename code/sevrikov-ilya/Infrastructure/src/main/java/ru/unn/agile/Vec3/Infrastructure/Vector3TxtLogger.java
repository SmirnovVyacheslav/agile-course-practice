package ru.unn.agile.Vec3.Infrastructure;

import ru.unn.agile.Vec3.ViewModel.Vector3ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class Vector3TxtLogger implements Vector3ILogger {
    private static final String FILENAME_EMPTY =
            "Text logger can't be initialized without filename.";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final String filename;
    private final BufferedWriter writerLog;

    private final StringBuilder stringBuilder = new StringBuilder();
    private final Formatter formatter = new Formatter(stringBuilder, Locale.ENGLISH);

    public Vector3TxtLogger(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException(FILENAME_EMPTY);
        }

        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        writerLog = logWriter;
    }

    @Override
    public void pushMessage(final String message) {
        try {
            writerLog.write(now() + " > " + message);
            writerLog.newLine();
            writerLog.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void pushMessage(final String format, final Object ... args) {
        stringBuilder.setLength(0);
        pushMessage(formatter.format(format, args).toString());
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

        return dataFormat.format(calendar.getTime());
    }
}
