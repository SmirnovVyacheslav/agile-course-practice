package ru.unn.agile.Vec3.ViewModel;

import org.junit.After;
import org.junit.Before;
import ru.unn.agile.Vec3.Model.Vector3;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Vector3ViewModelTest {
    private Vector3ViewModel viewModel;

    @Before
    public void initialize() {
        viewModel = new Vector3ViewModel(new Vector3FakeLogger());
    }

    @After
    public void shutdown() {
        viewModel = null;
    }

    @Test
    public void canGetNormOfDefaultFirstVector() {
        viewModel.compute(Vector3Operation.GET_NORM_FIRST_VECTOR);

        assertEquals("0.0", viewModel.getResultOfLastAction());
    }

    @Test
    public void canGetNormOfDefaultSecondVector() {
        viewModel.compute(Vector3Operation.GET_NORM_SECOND_VECTOR);

        assertEquals("0.0", viewModel.getResultOfLastAction());
    }

    @Test
    public void cannotNormalizeOfDefaultFirstVector() {
        viewModel.compute(Vector3Operation.NORMAlIZE_FIRST_VECTOR);

        assertEquals(Vector3ViewModelStatus.FIRST_VECTOR_SMALL_NUMBERS, viewModel.getStatus());
    }

    @Test
    public void cannotNormalizeOfDefaultSecondVector() {
        viewModel.compute(Vector3Operation.NORMALIZE_SECOND_VECTOR);

        assertEquals(Vector3ViewModelStatus.SECOND_VECTOR_SMALL_NUMBERS, viewModel.getStatus());
    }

    @Test
    public void canGetNormOfSpecificFirstVector() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);
        double norm = vector.getNorm();

        SetterVectorInViewModel.setFirstVector(vector, viewModel);

        viewModel.compute(Vector3Operation.GET_NORM_FIRST_VECTOR);

        assertEquals(Double.toString(norm), viewModel.getResultOfLastAction());
    }

    @Test
    public void canSetStatusWrongFormatWhenGetNormOfFirstVectorWithBadFormatting() {
        viewModel.setCoordX0("imposiburu");

        viewModel.compute(Vector3Operation.GET_NORM_FIRST_VECTOR);

        assertEquals(Vector3ViewModelStatus.FIRST_VECTOR_WRONG_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canSetStatusWrongFormatWhenGetNormOfSecondVectorWithBadFormatting() {
        viewModel.setCoordX1("imposiburu");

        viewModel.compute(Vector3Operation.GET_NORM_SECOND_VECTOR);

        assertEquals(Vector3ViewModelStatus.SECOND_VECTOR_WRONG_FORMAT, viewModel.getStatus());
    }



    @Test
    public void canSetStatusWrongFormatWhenCalculateDotProductWithBadFormatting() {
        viewModel.setCoordY0("imposiburu");

        viewModel.compute(Vector3Operation.CALCULATE_CROSS_PRODUCT);

        assertEquals(Vector3ViewModelStatus.FIRST_VECTOR_WRONG_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canSetStatusWrongFormatWhenCalculateCrossProductWithBadFormatting() {
        viewModel.setCoordY1("imposiburu");

        viewModel.compute(Vector3Operation.CALCULATE_CROSS_PRODUCT);

        assertEquals(Vector3ViewModelStatus.SECOND_VECTOR_WRONG_FORMAT, viewModel.getStatus());
    }


    @Test
    public void canGetNormOfSpecificSecondVector() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);
        double  norm   = vector.getNorm();

        SetterVectorInViewModel.setSecondVector(vector, viewModel);

        viewModel.compute(Vector3Operation.GET_NORM_SECOND_VECTOR);

        assertEquals(Double.toString(norm), viewModel.getResultOfLastAction());
    }

    @Test
    public void canNormalizeSpecificFirstVector() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);

        SetterVectorInViewModel.setFirstVector(vector, viewModel);

        viewModel.compute(Vector3Operation.NORMAlIZE_FIRST_VECTOR);
        vector.normalize();

        assertEquals(vector.toString(), viewModel.getResultOfLastAction());
    }

    @Test
    public void canNormalizeSpecificSecondVector() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);

        SetterVectorInViewModel.setSecondVector(vector, viewModel);

        viewModel.compute(Vector3Operation.NORMALIZE_SECOND_VECTOR);
        vector.normalize();

        assertEquals(vector.toString(), viewModel.getResultOfLastAction());
    }

    @Test
    public void canCalculateDotProduct() {
        Vector3 firstVector  = new Vector3(1.0, 2.0, 3.0);
        Vector3 secondVector = new Vector3(3.0, 2.0, 1.0);

        SetterVectorInViewModel.setFirstVector(firstVector, viewModel);
        SetterVectorInViewModel.setSecondVector(secondVector, viewModel);

        viewModel.compute(Vector3Operation.CALCULATE_DOT_PRODUCT);
        double dot = firstVector.dot(secondVector);

        assertEquals(Double.toString(dot), viewModel.getResultOfLastAction());
    }

    @Test
    public void getErrorByCalculateCrossProductWithDefaultVectors() {
        viewModel.compute(Vector3Operation.CALCULATE_CROSS_PRODUCT);

        assertEquals(Vector3ViewModelStatus.COPLANAR_VECTORS, viewModel.getStatus());
    }

    @Test
    public void canCalculateCrossProductFromSpecificVectors() {
        Vector3 firstVector  = new Vector3(1.0, 0.0, 0.0);
        Vector3 secondVector = new Vector3(0.0, 1.0, 0.0);

        SetterVectorInViewModel.setFirstVector(firstVector, viewModel);
        SetterVectorInViewModel.setSecondVector(secondVector, viewModel);

        viewModel.compute(Vector3Operation.CALCULATE_CROSS_PRODUCT);
        Vector3 crossProduct = firstVector.cross(secondVector);

        assertEquals(crossProduct.toString(), viewModel.getResultOfLastAction());
    }

    @Test
    public void isCorrectFirstComponentOfFistVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordX0(value);

        assertEquals(value, viewModel.getCoordX0());
    }

    @Test
    public void isCorrectSecondComponentOfFirstVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordY0(value);

        assertEquals(value, viewModel.getCoordY0());
    }

    @Test
    public void isCorrectThirdComponentOfFirstVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordZ0(value);

        assertEquals(value, viewModel.getCoordZ0());
    }

    @Test
    public void isCorrectFirstComponentOfSecondVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordX1(value);

        assertEquals(value, viewModel.getCoordX1());
    }

    @Test
    public void isCorrectSecondComponentOfSecondVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordY1(value);

        assertEquals(value, viewModel.getCoordY1());
    }

    @Test
    public void isCorrectThirdComponentOfSecondVectorFromStringValue() {
        String value = "666.0";

        viewModel.setCoordZ1(value);

        assertEquals(value, viewModel.getCoordZ1());
    }

    @Test
    public void isCorrectFirstComponentOfFirstVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordX0(value);

        assertEquals(value, viewModel.getCoordX0AsDouble(), Double.MIN_VALUE);
    }

    @Test
    public void isCorrectSecondComponentOfFirstVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordY0(value);

        assertEquals(value, viewModel.getCoordY0AsDouble(), Double.MIN_VALUE);
    }

    @Test
    public void isCorrectThirdComponentOfFirstVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordZ0(value);

        assertEquals(value, viewModel.getCoordZ0AsDouble(), Double.MIN_VALUE);
    }

    @Test
    public void isCorrectFirstComponentOfSecondVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordX1(value);

        assertEquals(value, viewModel.getCoordX1AsDouble(), Double.MIN_VALUE);
    }

    @Test
    public void isCorrectSecondComponentOfSecondVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordY1(value);

        assertEquals(value, viewModel.getCoordY1AsDouble(), Double.MIN_VALUE);
    }

    @Test
    public void isCorrectThirdComponentOfSecondVectorFromDoubleValue() {
        double value = 666.0;

        viewModel.setCoordZ1(value);

        assertEquals(value, viewModel.getCoordZ1AsDouble(), Double.MIN_VALUE);
    }
}
