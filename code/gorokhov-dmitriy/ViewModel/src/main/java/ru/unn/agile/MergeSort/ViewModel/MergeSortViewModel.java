package ru.unn.agile.MergeSort.ViewModel;

import ru.unn.agile.MergeSort.Model.MergeSort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MergeSortViewModel {
    private boolean sortButtonEnabled;
    private String sourceArray;
    private String resultArray;
    private SortingOrder sortingOrder;
    private SortingArrayStatus sortingArrayStatus;
    private IMergeSortLogger logger;

    public MergeSortViewModel(final IMergeSortLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Argument is null pointer");
        }
        this.logger = logger;

        sortButtonEnabled = false;
        sourceArray = "";
        resultArray = "";
        sortingOrder = SortingOrder.ASCENDING;
        sortingArrayStatus = SortingArrayStatus.EMPTY;
    }

    public void sort() {
        logger.write(sortButtonPressedLoggerRecord());

        Double[] sortingArray = parseSourceArrayToSortingArray();

        MergeSort.sort(sortingArray);

        if (sortingOrder == SortingOrder.DESCENDING) {
            Collections.reverse(Arrays.asList(sortingArray));
        }

        parseSortingArrayToResultArray(sortingArray);

        logger.write(sourceArraySortedLoggerRecord());
    }

    public String getResultArray() {
        return resultArray;
    }

    public void setSortingArray(final String sourceArray) {
        if (!canParseSourceArray(sourceArray)) {
            return;
        }

        this.sourceArray = sourceArray;
    }

    public boolean isSortButtonEnabled() {
        return sortButtonEnabled;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(final SortingOrder sortingOrder) {
        if (this.sortingOrder != sortingOrder) {
            this.sortingOrder = sortingOrder;
            logger.write(sortingOrderChangedLoggerRecord());
        }
    }

    public String getSortingArrayStatus() {
        return sortingArrayStatus.toString();
    }

    public IMergeSortLogger getLogger() {
        return logger;
    }

    public enum SortingOrder {
        ASCENDING("Ascending"),
        DESCENDING("Descending");

        SortingOrder(final String order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return order;
        }

        private final String order;
    }

    public enum SortingArrayStatus {
        EMPTY("Please input source array"),
        WRONG_FORMAT("Source array has wrong format"),
        VALID_FORMAT("Press button to sort");

        SortingArrayStatus(final String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }

        private final String status;
    }

    public enum LogRecords {
        SORT_BUTTON_PRESSED("Sorting source array: "),
        SOURCE_ARRAY_SORTED("Result array: "),
        SORTING_ORDER_CHANGED("Sorting order changed to ");

        LogRecords(final String record) {
            this.record = record;
        }

        @Override
        public String toString() {
            return record;
        }

        private String record;
    }

    private boolean canParseSourceArray(final String sourceArray) {
        String[] elementsArray = sourceArray.split(" ");

        try {
            for (String anElementsArray : elementsArray) {
                Double.parseDouble(anElementsArray);
            }
        } catch (NumberFormatException e) {
            if (Objects.equals(sourceArray, "")) {
                sortingArrayStatus = SortingArrayStatus.EMPTY;
            } else {
                sortingArrayStatus = SortingArrayStatus.WRONG_FORMAT;
            }
            sortButtonEnabled = false;
            return false;
        }

        sortingArrayStatus = SortingArrayStatus.VALID_FORMAT;
        sortButtonEnabled = true;
        return true;
    }

    private Double[] parseSourceArrayToSortingArray() {
        String[] elementsArray = sourceArray.split(" ");
        Double[] sortingArray = new Double[elementsArray.length];

        for (int i = 0; i < elementsArray.length; i++) {
            sortingArray[i] = Double.parseDouble(elementsArray[i]);
        }

        return sortingArray;
    }

    private void parseSortingArrayToResultArray(final Double[] sortingArray) {
        resultArray = "";

        for (Double aSortingArray : sortingArray) {
            resultArray += aSortingArray.toString() + " ";
        }
        resultArray = resultArray.trim();
    }

    private String sortingOrderChangedLoggerRecord() {
        return LogRecords.SORTING_ORDER_CHANGED.toString() + sortingOrder.toString();
    }

    private String sortButtonPressedLoggerRecord() {
        return LogRecords.SORT_BUTTON_PRESSED.toString() + sourceArray;
    }

    private String sourceArraySortedLoggerRecord() {
        return LogRecords.SOURCE_ARRAY_SORTED.toString() + resultArray;
    }
}
