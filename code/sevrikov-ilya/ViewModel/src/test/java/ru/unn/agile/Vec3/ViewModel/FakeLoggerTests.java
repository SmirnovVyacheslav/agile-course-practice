package ru.unn.agile.Vec3.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class FakeLoggerTests {
    private Vector3FakeLogger fakeLogger;

    @Before
    public void initialize() {
        fakeLogger = new Vector3FakeLogger();
    }

    @After
    public void shutdown() {
        fakeLogger = null;
    }

    @Test
    public void byDefaultFakeLoggerIsEmpty() {
        assertEquals(fakeLogger.size(), 0);
    }

    @Test
    public void pushMessageAddsOneMessage() {
        final String message = "I never asked for this";

        fakeLogger.pushMessage(message);

        assertEquals(1, fakeLogger.size());
    }

    @Test
    public void pushMessageIsCorrect() {
        final String message = "Ave Satan!";

        fakeLogger.pushMessage(message);

        assertEquals(message, fakeLogger.getLastMessage());
    }

    @Test
    public void pushFormattedMessageAddsOneMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.ENGLISH);

        final String format = "%1$f * %2$f = %3$f";
        final String formattedMessage =
                formatter.format(format, 5.0f, 5.0f, 25.0f).toString();

        fakeLogger.pushMessage(format, 5.0f, 5.0f, 25.0f);

        assertEquals(1, fakeLogger.size());
    }

    @Test
    public void pushFormattedMessageIsCorrect() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.ENGLISH);

        final String format = "%1$f + %2$f = %3$f";
        final String formattedMessage =
                formatter.format(format, 5.0f, 3.0f, 5.0f + 3.0f).toString();

        fakeLogger.pushMessage(format, 5.0f, 3.0f, 5.0f + 3.0f);

        assertEquals(formattedMessage, fakeLogger.getLastMessage());
    }

    @Test
    public void canPushSeveralMessages() {
        final int count = 6;
        ArrayList<String> messages = new ArrayList<>();

        for (int idx = 0; idx < count; ++idx) {
            String message = Integer.toString(idx);

            messages.add(message);
            fakeLogger.pushMessage(message);
        }

        assertEquals(messages, fakeLogger.getLog());
    }
}
