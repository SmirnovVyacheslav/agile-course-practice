package ru.unn.agile.Vec3.Infrastructure;

import ru.unn.agile.Vec3.ViewModel.Vector3ViewModel;
import ru.unn.agile.Vec3.ViewModel.Vector3ViewModelLoggerTest;

public class Vector3ViewModelWithTxtLoggerTests extends Vector3ViewModelLoggerTest {
    @Override
    public void initialize() {
        Vector3TxtLogger txtLogger = new Vector3TxtLogger("./ViewModelWithTxtLoggerTests.txt");
        super.setViewModel(new Vector3ViewModel(txtLogger));
    }
}
