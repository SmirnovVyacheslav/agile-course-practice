package ru.unn.agile.Polinom.infrastructure;

import ru.unn.agile.Polinom.viewmodel.PolinomViewModel;
import ru.unn.agile.Polinom.viewmodel.PolinomViewModelWithTxtLoggerTests;

public class PolinomViewModelWithRealTxtLoggerTests extends PolinomViewModelWithTxtLoggerTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("./ViewModel_with_TxtLogger_Tests.log");
        super.setExternalPolinomlViewModel(new PolinomViewModel(realLogger));
    }
}
