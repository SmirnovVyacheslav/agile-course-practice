package ru.unn.agile.Vec3.Infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class Vector3TxtLoggerTests {
    private static final String LOG_FILE = "./log.txt";
    private Vector3TxtLogger logger;

    @Before
    public void initialize() {
        logger = new Vector3TxtLogger(LOG_FILE);
    }

    @After
    public void shutdown() {
        logger = null;
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test (expected = IllegalArgumentException.class)
    public void cannotCreateLoggerWithoutFilename() {
        logger = new Vector3TxtLogger(null);
    }

    @Test
    public void canPushMessage() {
        final String testMessage = "WHERE IS THE TRIGGER?!";

        logger.pushMessage(testMessage);

        assertThat(logger.getLog().get(0), containsString(testMessage));
    }

    @Test
    public void canPushTwoMessages() {
        final String firstMessage =
                "It is better to die for the Emperor than to live for yourself!";
        final String secondMessage =
                "I never asked for this!";

        logger.pushMessage(firstMessage);
        logger.pushMessage(secondMessage);

        List<String> messages = logger.getLog();

        assertThat(messages.get(0), containsString(firstMessage));
        assertThat(messages.get(1), containsString(secondMessage));
    }
}
