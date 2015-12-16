package ru.unn.agile.MergeSort.Infrastructure;

import ru.unn.agile.MergeSort.ViewModel.MergeSortViewModel;
import ru.unn.agile.MergeSort.ViewModel.MergeSortViewModelTests;

public class MergeSortViewModelFileLoggerTests extends MergeSortViewModelTests {
    @Override
    public void setUp() {
        MergeSortFileLogger fileLogger =
                new MergeSortFileLogger("./MergeSortViewModelFileLoggerTests.log");
        super.setMergeSortViewModel(new MergeSortViewModel(fileLogger));
    }
}
