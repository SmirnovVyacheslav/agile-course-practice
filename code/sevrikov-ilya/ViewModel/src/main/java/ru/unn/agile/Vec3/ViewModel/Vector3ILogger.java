package ru.unn.agile.Vec3.ViewModel;

import java.util.List;

public interface Vector3ILogger {
    void pushMessage(final String message);

    void pushMessage(final String format, final Object ... args);

    List<String> getLog();
}
