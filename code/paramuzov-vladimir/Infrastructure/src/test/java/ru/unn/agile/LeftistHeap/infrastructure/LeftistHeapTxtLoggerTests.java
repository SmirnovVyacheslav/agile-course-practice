package ru.unn.agile.LeftistHeap.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LeftistHeap.viewmodel.ILeftistHeapLogger;

import java.io.File;

import static org.junit.Assert.*;
import static ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapRegexMatcher.matches;

public class LeftistHeapTxtLoggerTests {
    private static final String LOG_FILENAME = "./paramuzov-vladimir-logger-test.log";
    private static final String TEST_MESSAGE = "Test message for txt logger";
    private LeftistHeapTxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new LeftistHeapTxtLogger(LOG_FILENAME);
    }

    @Test
    public void canCreateLeftistHeapTxtLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void isLoggerCreateTxtFileOnDisk() {
        assertTrue(new File(LOG_FILENAME).isFile());
    }

    @Test
    public void canWriteMessageToTxtLog() {
        txtLogger.log(TEST_MESSAGE);

        assertEquals(1, txtLogger.getLog().size());
    }

    @Test
    public void isTxtLogContainProperMessage() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getMessage(0);

        assertThat(message, matches(ILeftistHeapLogger.DATE_REGEX + TEST_MESSAGE));
    }

    @Test
    public void canWriteTenMessagesToLog() {
        for (int i = 0; i < 10; i++) {
            txtLogger.log(TEST_MESSAGE + i);
        }

        assertEquals(10, txtLogger.getLog().size());
    }

    @Test
    public void canReadSecondMessageFromLog() {
        txtLogger.log(TEST_MESSAGE + "0");
        txtLogger.log(TEST_MESSAGE + "1");

        String message = txtLogger.getMessage(1);

        assertThat(message, matches(ILeftistHeapLogger.DATE_REGEX + TEST_MESSAGE + "1"));
    }

    @Test
    public void isLogMessageContainDateTime() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getMessage(0);

        assertThat(message, matches(ILeftistHeapLogger.DATE_REGEX + ".*"));
    }
}
