package ru.unn.agile.MergeSort.ViewModel;

import java.util.List;

public interface IMergeSortLogger {
    void write(final String record);

    String read(final int recordNumber);

    List<String> getRecordsList();
}
