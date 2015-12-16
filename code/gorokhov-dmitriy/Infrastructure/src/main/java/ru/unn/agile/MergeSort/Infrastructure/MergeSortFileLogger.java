package ru.unn.agile.MergeSort.Infrastructure;

import ru.unn.agile.MergeSort.ViewModel.IMergeSortLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MergeSortFileLogger implements IMergeSortLogger {
    private final String outputFileName;
    private BufferedWriter recordsToFileWriter;

    public MergeSortFileLogger(final String outputFileName) {
        this.outputFileName = outputFileName;

        try {
            recordsToFileWriter = new BufferedWriter(new FileWriter(outputFileName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void write(final String record) {
        try {
            recordsToFileWriter.write("[" + getRecordsList().size() + "] " + record);
            recordsToFileWriter.newLine();
            recordsToFileWriter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public String read(final int recordNumber) {
        BufferedReader fileReader;
        String record = null;
        try {
            fileReader = new BufferedReader(new FileReader(outputFileName));

            int currentRecordNumber = 0;
            while (currentRecordNumber <= recordNumber) {
                record = fileReader.readLine();
                currentRecordNumber++;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return record;
    }

    @Override
    public List<String> getRecordsList() {
        List<String> recordsList = new ArrayList<>();

        try {
            recordsList = Files.readAllLines(Paths.get(outputFileName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return recordsList;
    }
}
