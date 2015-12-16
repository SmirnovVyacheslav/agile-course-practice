package ru.unn.agile.LeftistHeap.infrastructure;

import org.junit.Before;
import ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModel;
import ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModelTest;

public class LeftistHeapViewModelWithTxtLoggerTest extends LeftistHeapViewModelTest {
    @Before
    public void setUp() {
        final String logFileName = "./paramuzov-vladimir-ViewModelWithTxtLogger-test.log";
        LeftistHeapTxtLogger txtLogger = new LeftistHeapTxtLogger(logFileName);
        super.setViewModel(new LeftistHeapViewModel(txtLogger));
    }
}
