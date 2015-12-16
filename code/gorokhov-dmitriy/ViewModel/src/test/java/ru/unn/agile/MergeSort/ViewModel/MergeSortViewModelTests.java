package ru.unn.agile.MergeSort.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static ru.unn.agile.MergeSort.ViewModel.MergeSortRegexMatcher.matches;
import static ru.unn.agile.MergeSort.ViewModel.MergeSortViewModel.SortingOrder;
import static ru.unn.agile.MergeSort.ViewModel.MergeSortViewModel.SortingArrayStatus;
import static ru.unn.agile.MergeSort.ViewModel.MergeSortViewModel.LogRecords;

public class MergeSortViewModelTests {
    public void setMergeSortViewModel(final MergeSortViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new MergeSortViewModel(new FakeMergeSortLogger());
    }

    @Test
    public void isResultArraySetEmptyByDefault() {
        assertEquals("", viewModel.getResultArray());
    }

    @Test
    public void isSortButtonDisabledByDefault() {
        assertFalse(viewModel.isSortButtonEnabled());
    }

    @Test
    public void isSortButtonEnabledWhenSourceArrayEntered() {
        viewModel.setSortingArray("0");

        assertTrue(viewModel.isSortButtonEnabled());
    }

    @Test
    public void isSortButtonDisabledWhenSourceArrayCleared() {
        viewModel.setSortingArray("0");
        viewModel.setSortingArray("");

        assertFalse(viewModel.isSortButtonEnabled());
    }

    @Test
    public void isSortButtonDisabledWhenInvalidSourceArrayEntered() {
        viewModel.setSortingArray("5 3 tg");

        assertFalse(viewModel.isSortButtonEnabled());
    }

    @Test
    public void isSortButtonEnabledWhenSourceArrayCorrected() {
        viewModel.setSortingArray("5 3 tg");
        viewModel.setSortingArray("5 3 7");

        assertTrue(viewModel.isSortButtonEnabled());
    }

    @Test
    public void isSortingOrderSetAscendingByDefault() {
        assertEquals(SortingOrder.ASCENDING, viewModel.getSortingOrder());
    }

    @Test
    public void canConvertSortingOrderToString() {
        assertEquals("Ascending", viewModel.getSortingOrder().toString());
    }

    @Test
    public void canChangeSortingOrder() {
        viewModel.setSortingOrder(SortingOrder.DESCENDING);

        assertEquals(SortingOrder.DESCENDING, viewModel.getSortingOrder());
    }

    @Test
    public void isResultArrayEmptyWhenInvalidSourceArrayEntered() {
        viewModel.setSortingArray("we fd 132");

        assertEquals("", viewModel.getResultArray());
    }

    @Test
    public void isResultArrayEmptyBeforeSortButtonPress() {
        viewModel.setSortingArray("46 3 132");

        assertEquals("", viewModel.getResultArray());
    }

    @Test
    public void isResultArrayCorrectWhenSortingIntegerSourceArray() {
        viewModel.setSortingArray("34 -43 11 43 34");
        viewModel.sort();

        assertEquals("-43.0 11.0 34.0 34.0 43.0", viewModel.getResultArray());
    }

    @Test
    public void isResultArrayCorrectWhenSortingFloatingPointSourceArray() {
        viewModel.setSortingArray("34.5 -43.2 11.2 43.4 34.1");
        viewModel.sort();

        assertEquals("-43.2 11.2 34.1 34.5 43.4", viewModel.getResultArray());
    }

    @Test
    public void isResultArrayCorrectWhenSortingIntegerSourceArrayByDescendingOrder() {
        viewModel.setSortingArray("5 -3 -3 0 1");
        viewModel.setSortingOrder(SortingOrder.DESCENDING);
        viewModel.sort();

        assertEquals("5.0 1.0 0.0 -3.0 -3.0", viewModel.getResultArray());
    }

    @Test
    public void isResultArrayCorrectWhenSortingFloatingPointSourceArrayByDescendingOrder() {
        viewModel.setSortingArray("34.5 -43.2 11.2 43.4 34.1");
        viewModel.setSortingOrder(SortingOrder.DESCENDING);
        viewModel.sort();

        assertEquals("43.4 34.5 34.1 11.2 -43.2", viewModel.getResultArray());
    }

    @Test
    public void canConvertSourceArrayStatusToString() {
        assertEquals("Please input source array",
                     MergeSortViewModel.SortingArrayStatus.EMPTY.toString());
    }

    @Test
    public void isSourceArrayStatusSetEmptyByDefault() {
        assertEquals(SortingArrayStatus.EMPTY.toString(),
                     viewModel.getSortingArrayStatus());
    }

    @Test
    public void isSourceArrayStatusSetWrongFormatWhenSourceArrayInvalid() {
        viewModel.setSortingArray("12 ds");

        assertEquals(SortingArrayStatus.WRONG_FORMAT.toString(),
                     viewModel.getSortingArrayStatus());
    }

    @Test
    public void isSourceArrayStatusSetValidFormatWhenSourceArrayValid() {
        viewModel.setSortingArray("56.2 123.1");

        assertEquals(SortingArrayStatus.VALID_FORMAT.toString(),
                     viewModel.getSortingArrayStatus());
    }

    @Test
    public void isSourceArrayStatusSetEmptyWhenSourceArrayCleared() {
        viewModel.setSortingArray("56.2 123.1");
        viewModel.setSortingArray("");

        assertEquals(SortingArrayStatus.EMPTY.toString(),
                     viewModel.getSortingArrayStatus());
    }

    @Test
    public void isSourceArrayStatusSetValidFormatWhenSourceArrayCorrected() {
        viewModel.setSortingArray("12 ds");
        viewModel.setSortingArray("56.2 123.1");

        assertEquals(SortingArrayStatus.VALID_FORMAT.toString(),
                     viewModel.getSortingArrayStatus());
    }

    @Test
    public void canCreateMergeSortViewModelWithFakeLogger() {
        MergeSortViewModel viewModelWithLogger = new MergeSortViewModel(new FakeMergeSortLogger());

        assertNotNull(viewModelWithLogger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateMergeSortViewModelWithNullPointerLogger() {
        new MergeSortViewModel(null);
    }

    @Test
    public void isLoggerEmptyByDefault() {
        assertEquals(0, viewModel.getLogger().getRecordsList().size());
    }

    @Test
    public void isCorrectRecordWrittenToLogWhenSortingOrderChanged() {
        viewModel.setSortingOrder(SortingOrder.DESCENDING);

        assertThat(viewModel.getLogger().read(0), matches(".*"
                + LogRecords.SORTING_ORDER_CHANGED.toString() + SortingOrder.DESCENDING + ".*"));
    }

    @Test
    public void isLogNotChangedWhenSortingOrderChangedToSame() {
        viewModel.setSortingOrder(SortingOrder.ASCENDING);

        assertEquals(0, viewModel.getLogger().getRecordsList().size());
    }

    @Test
    public void isCorrectRecordWrittenToLogWhenSortButtonPressed() {
        viewModel.setSortingArray("12 31 14");
        viewModel.sort();

        assertThat(viewModel.getLogger().read(0), matches(".*"
                + LogRecords.SORT_BUTTON_PRESSED.toString() + "12 31 14" + ".*"));
    }

    @Test
    public void isCorrectRecordWrittenToLogWhenSortingFinished() {
        viewModel.setSortingArray("12 31 14");
        viewModel.sort();

        assertThat(viewModel.getLogger().read(1), matches(".*"
                + LogRecords.SOURCE_ARRAY_SORTED.toString() + viewModel.getResultArray() + ".*"));
    }

    private MergeSortViewModel viewModel;
}
