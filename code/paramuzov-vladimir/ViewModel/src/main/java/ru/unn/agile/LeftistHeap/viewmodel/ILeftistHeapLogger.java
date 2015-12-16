package ru.unn.agile.LeftistHeap.viewmodel;

import java.util.List;

public interface ILeftistHeapLogger {
    String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    String DATE_REGEX = "^<\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}:\\d{2}> ";

    void log(final String message);

    List<String> getLog();

    String getMessage(final int messageIndex);
}
