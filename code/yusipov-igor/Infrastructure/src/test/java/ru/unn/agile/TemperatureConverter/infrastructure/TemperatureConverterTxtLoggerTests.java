package ru.unn.agile.TemperatureConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TemperatureConverterTxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTest.log";
    private TemperatureConverterTxtLogger textLog;

    @Before
    public void setUp() {
        textLog = new TemperatureConverterTxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithUsingFileName() {
        assertNotNull(textLog);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteMessageToLog() {
        String testMessage = "Test message";
        textLog.log(testMessage);

        String message = textLog.getLog().get(0);

        assertThat(message, containsString(testMessage));
    }

    @Test
    public void canWriteSomeLogMessages() {
        String[] messages = {"First test message", "Second test message"};
        textLog.log(messages[0]);
        textLog.log(messages[1]);

        List<String> loggedMessages = textLog.getLog();

        assertThat(loggedMessages.get(0), containsString(messages[0]));
        assertThat(loggedMessages.get(1), containsString(messages[1]));
    }

    @Test
    public void doesLoggerWriteDateAndTime() {
        String testMessage = "Test message";
        textLog.log(testMessage);

        String message = textLog.getLog().get(0);

        assertTrue(Pattern.matches("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*", message));
    }
}
