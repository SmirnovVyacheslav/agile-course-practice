package ru.unn.agile.LeftistHeap.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapRegexMatcher.matches;
import static ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModel.Operations;
import static ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModel.Errors;
import static ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModel.LogMessages;

public class LeftistHeapViewModelTest {
    private LeftistHeapViewModel viewModel;

    public void setViewModel(final LeftistHeapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new LeftistHeapViewModel(new FakeLeftistHeapLogger());
    }

    @Test
    public void byDefaultApplyButtonIsDisabled() {
        assertFalse(viewModel.isApplyButtonEnabled());
    }

    @Test
    public void whenInputIntegerToKeyValueFieldInsertButtonIsEnabled() {
        viewModel.setKeyValue("1");

        assertTrue(viewModel.isApplyButtonEnabled());
    }

    @Test
    public void whenClearKeyValueFieldApplyButtonIsDisabled() {
        viewModel.setKeyValue("1");
        viewModel.setKeyValue("");

        assertFalse(viewModel.isApplyButtonEnabled());
    }

    @Test
    public void whenInsertOneIntegerToHeapContentDisplayed() {
        viewModel.setKeyValue("1");
        viewModel.setOperation(Operations.INSERT);
        viewModel.applyOperation();

        assertEquals("[1]", viewModel.getHeapContent());
    }

    @Test
    public void whenInsertTwoIntegersToHeapContentDisplayed() {
        viewModel.setOperation(Operations.INSERT);
        viewModel.setKeyValue("1");
        viewModel.applyOperation();
        viewModel.setKeyValue("2");
        viewModel.applyOperation();

        assertEquals("[1, 2]", viewModel.getHeapContent());
    }

    @Test
    public void whenInputWrongKeyValueErrorDisplayed() {
        viewModel.setKeyValue("afc");

        assertEquals(Errors.FIELD_BAD_FORMAT.toString(),
                     viewModel.getErrorText());
    }

    @Test
    public void whenInputWrongValueToKeyFieldApplyButtonIsDisabled() {
        viewModel.setKeyValue("afc");

        assertFalse(viewModel.isApplyButtonEnabled());
    }

    @Test
    public void byDefaultOperationIsInsert() {
        assertEquals(Operations.INSERT.toString(),
                     viewModel.getOperation().toString());
    }

    @Test
    public void canChangeOperation() {
        viewModel.setOperation(Operations.DELETE);

        assertEquals(Operations.DELETE, viewModel.getOperation());
    }

    @Test
    public void whenTryingToDeleteNotExistingElementErrorDisplayed() {
        viewModel.setKeyValue("1");
        viewModel.setOperation(Operations.DELETE);
        viewModel.applyOperation();

        assertEquals(Errors.VALUE_TO_DELETE_NOT_FOUND.toString(),
                     viewModel.getErrorText());
    }

    @Test
    public void canDeleteExistingElementFromHeap() {
        viewModel.setOperation(Operations.INSERT);
        viewModel.setKeyValue("1");
        viewModel.applyOperation();
        viewModel.setOperation(Operations.DELETE);
        viewModel.applyOperation();

        assertEquals("[]", viewModel.getHeapContent());
    }

    @Test
    public void whenFixKeyValueFormatErrorHide() {
        viewModel.setKeyValue("afc");
        viewModel.setKeyValue("1");

        assertEquals("", viewModel.getErrorText());
    }

    @Test
    public void canCreateLeftistHeapViewModelWithLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateLeftistHeapViewModelWithNullLogger() {
        new LeftistHeapViewModel(null);
    }

    @Test
    public void byDefaultLogIsEmpty() {
        assertEquals(0, viewModel.getLogger().getLog().size());
    }

    @Test
    public void isEditingKeyValueFieldAddNewMessageToLog() {
        viewModel.setKeyValue("10");

        viewModel.valueFieldFocusLost();

        assertEquals(1, viewModel.getLogger().getLog().size());
    }

    @Test
    public void isLogContainProperMessageAfterKeyValueFieldEdited() {
        viewModel.setKeyValue("-10");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLogger().getMessage(0);

        assertThat(logMessage, matches(ILeftistHeapLogger.DATE_REGEX
                                        + LogMessages.KEY_VALUE_FIELD_CHANGED.toString()
                                        + LeftistHeapViewModel.KEY_VALUE_REGEX));
    }

    @Test
    public void isPressingApplyOperationButtonAddNewMessageToLog() {
        viewModel.setKeyValue("10");

        viewModel.applyOperation();

        assertEquals(1, viewModel.getLogger().getLog().size());
    }

    @Test
    public void isLogContainProperMessageAfterApplyInsertOperation() {
        viewModel.setKeyValue("10");

        viewModel.applyOperation();
        String logMessage = viewModel.getLogger().getMessage(0);

        assertThat(logMessage, matches(ILeftistHeapLogger.DATE_REGEX
                                        + LogMessages.BUTTON_PRESSED.toString()
                                        + "Operation: "
                                        + Operations.INSERT.toString()
                                        + "; Value: "
                                        + LeftistHeapViewModel.KEY_VALUE_REGEX));
    }

    @Test
    public void isChangingOperationAddNewMessageToLog() {
        viewModel.setOperation(Operations.DELETE);

        assertEquals(1, viewModel.getLogger().getLog().size());
    }

    @Test
    public void isLogContainProperMessageAfterChangeOperationToDelete() {
        viewModel.setOperation(Operations.DELETE);
        String logMessage = viewModel.getLogger().getMessage(0);

        assertThat(logMessage, matches(ILeftistHeapLogger.DATE_REGEX
                                        + LogMessages.OPERATION_CHANGED.toString()
                                        + "`" + Operations.DELETE.toString() + "`"));
    }
}
