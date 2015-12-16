package ru.unn.agile.MergeSort.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeMergeSortLogger implements IMergeSortLogger {
    @Override
    public void write(final String record) {
        recordsList.add(record);
    }

    @Override
    public String read(final int recordNumber) {
        if (recordNumber >= recordsList.size()) {
            return null;
        }

        return recordsList.get(recordNumber);
    }

    @Override
    public List<String> getRecordsList() {
        return recordsList;
    }

    private ArrayList<String> recordsList = new ArrayList<>();
}

