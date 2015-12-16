package ru.unn.agile.Vec3.ViewModel;

import ru.unn.agile.Vec3.Model.Vector3;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Vector3ViewModelLoggerParametrizedTest {
    private Vector3ViewModel viewModel;
    private Vector3 firstVector;
    private Vector3 secondVector;
    private Vector3Operation operation;

    public Vector3ViewModelLoggerParametrizedTest(final Vector3 firstVector,
                                                  final Vector3 secondVector,
                                                  final Vector3Operation operation) {
        this.firstVector = firstVector;
        this.secondVector = secondVector;
        this.operation = operation;
    }

    public void setViewModel(final Vector3ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Parameterized.Parameters
    public static Collection operations() {
        return Arrays.asList(new Object[][] {
                {new Vector3(1.0, 2.0, 3.0), new Vector3(4.0, 5.0, 6.0),
                        Vector3Operation.GET_NORM_FIRST_VECTOR},
                {new Vector3(7.0, 8.0, 9.0), new Vector3(8.0, 7.0, 6.0),
                        Vector3Operation.GET_NORM_SECOND_VECTOR},
                {new Vector3(5.0, 4.0, 3.0), new Vector3(2.0, 1.0, 2.0),
                        Vector3Operation.NORMAlIZE_FIRST_VECTOR},
                {new Vector3(3.0, 4.0, 5.0), new Vector3(6.0, 7.0, 8.0),
                        Vector3Operation.NORMALIZE_SECOND_VECTOR},
                {new Vector3(9.0, 8.0, 7.0), new Vector3(6.0, 5.0, 4.0),
                        Vector3Operation.CALCULATE_DOT_PRODUCT},
                {new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 1.0, 0.0),
                        Vector3Operation.CALCULATE_CROSS_PRODUCT}
        });
    }

    @Before
    public void initialize() {
        viewModel = new Vector3ViewModel(new Vector3FakeLogger());
    }

    @After
    public void shutdown() {
        viewModel = null;
    }

    @Test
    public void viewModelCanLogOperation() {
        SetterVectorInViewModel.setFirstVector(firstVector, viewModel);
        SetterVectorInViewModel.setSecondVector(secondVector, viewModel);

        viewModel.compute(operation);

        String outputMessage = getOutputMessage(operation);

        assertEquals(1, viewModel.getLog().size());
        assertEquals(outputMessage, viewModel.getLog().get(0));
    }

    private String getOutputMessage(final Vector3Operation operation) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.ENGLISH);

        return formatter.format(viewModel.getOutputFormat(),
                operation.toString(), viewModel.getStatus(),
                viewModel.getCoordX0(), viewModel.getCoordY0(), viewModel.getCoordZ0(),
                viewModel.getCoordX1(), viewModel.getCoordY1(), viewModel.getCoordZ1(),
                viewModel.getResultOfLastAction()).toString();
    }
}
