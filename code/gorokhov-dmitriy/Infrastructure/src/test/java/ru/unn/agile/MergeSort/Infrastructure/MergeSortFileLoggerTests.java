package ru.unn.agile.MergeSort.Infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static ru.unn.agile.MergeSort.ViewModel.MergeSortRegexMatcher.matches;

public class MergeSortFileLoggerTests {
    @Before
    public void setUp() {
        fileLogger = new MergeSortFileLogger(OUTPUT_FILE_NAME);
    }

    @Test
    public void canCreateFileWithOutputFileName() {
        assertTrue(new File(OUTPUT_FILE_NAME).isFile());
    }

    @Test
    public void canCreateFileLoggerWithOutputFileName() {
        assertNotNull(fileLogger);
    }

    @Test
    public void canWriteRecordWithFileLogger() {
        fileLogger.write("Sample");

        assertEquals(1, fileLogger.getRecordsList().size());
    }

    @Test
    public void canReadRecordWithFileLogger() {
        fileLogger.write("Sample");

        assertThat(fileLogger.read(0), matches(".*" + "Sample" + ".*"));
    }

    @Test
    public void canWriteSeveralRecordsWithFileLogger() {
        fileLogger.write("Sample1");
        fileLogger.write("Sample2");
        fileLogger.write("Sample3");

        assertEquals(3, fileLogger.getRecordsList().size());
    }

    @Test
    public void canReadRecordsListFromFileLogger() {
        fileLogger.write("Sample1");
        fileLogger.write("Sample2");

        assertEquals(2, fileLogger.getRecordsList().size());
    }

    @Test
    public void isReadRecordReturnNullWhenRecordNumberIsOutOfBounds() {
        fileLogger.write("Sample1");
        fileLogger.write("Sample2");

        assertNull(fileLogger.read(2));
    }

    @Test
    public void isSecondRecordMarkedWithCorrectNumber() {
        fileLogger.write("Sample1");
        fileLogger.write("Sample2");

        assertThat(fileLogger.read(1), matches(".*" + "[1]" + ".*" + "Sample" + ".*"));
    }

    private static final String OUTPUT_FILE_NAME = "./MergeSortFileLoggerTests.log";
    private MergeSortFileLogger fileLogger;
}



