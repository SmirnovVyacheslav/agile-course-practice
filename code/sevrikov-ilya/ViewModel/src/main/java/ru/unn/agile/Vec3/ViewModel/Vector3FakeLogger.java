package ru.unn.agile.Vec3.ViewModel;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public final class Vector3FakeLogger implements Vector3ILogger {
    private final ArrayList<String> messages = new ArrayList<String>();

    private final StringBuilder stringBuilder = new StringBuilder();
    private final Formatter formatter = new Formatter(stringBuilder, Locale.ENGLISH);

    @Override
    public void pushMessage(final String message) {
        messages.add(message);
    }

    @Override
    public void pushMessage(final String format, final Object ... args) {
        messages.add(formatter.format(format, args).toString());
    }

    @Override
    public List<String> getLog() {
        return messages;
    }

    public int size() {
        return messages.size();
    }

    public String getLastMessage() {
        return messages.get(messages.size() - 1);
    }
}
