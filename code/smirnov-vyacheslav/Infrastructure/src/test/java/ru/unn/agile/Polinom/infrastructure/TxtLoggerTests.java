package ru.unn.agile.Polinom.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests.log";
    private static final String TEST_MESSAGE = "Test message";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        assertTrue(new File(FILENAME).isFile());
    }

    @Test
    public void canWriteLogMessage() {
        txtLogger.log(TEST_MESSAGE);

        assertTrue(txtLogger.getLog().get(0).matches(".*" + TEST_MESSAGE + "$"));
    }

    @Test
    public void canWriteSeveralLogMessages() {
        String[] messages = {TEST_MESSAGE + " 1", TEST_MESSAGE + " 2"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        for (int i = 0; i < messages.length; i++) {
            assertTrue(txtLogger.getLog().get(i).matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getLog().get(0);
        assertTrue(txtLogger.getLog().get(0).
            matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
