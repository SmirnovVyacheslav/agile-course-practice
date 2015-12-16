package ru.unn.agile.Vec3.ViewModel;

import ru.unn.agile.Vec3.Model.Vector3;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Vector3ViewModel {
    private String coordX0 = "0.0";
    private String coordY0 = "0.0";
    private String coordZ0 = "0.0";

    private String coordX1 = "0.0";
    private String coordY1 = "0.0";
    private String coordZ1 = "0.0";

    private String resultOfLastAction = "";
    private String status = Vector3ViewModelStatus.OK;

    private final DecimalFormat formatter;

    private final Vector3ILogger logger;

    public Vector3ViewModel(final Vector3ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException(Vector3ViewModelStatus.LOGGER_CANNOT_BE_NULL);
        }
        this.logger = logger;

        formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        formatter.applyPattern("###.####");
    }

    public void setCoordX0(final String coordX0) {
        this.coordX0 = coordX0;
    }

    public void setCoordX0(final double coordX0) {
        this.coordX0 = Double.toString(coordX0);
    }

    public void setCoordY0(final String coordY0) {
        this.coordY0 = coordY0;
    }

    public void setCoordY0(final double coordY0) {
        this.coordY0 = Double.toString(coordY0);
    }

    public void setCoordZ0(final String coordZ0) {
        this.coordZ0 = coordZ0;
    }

    public void setCoordZ0(final double coordZ0) {
        this.coordZ0 = Double.toString(coordZ0);
    }

    public void setCoordX1(final String coordX1) {
        this.coordX1 = coordX1;
    }

    public void setCoordX1(final double coordX1) {
        this.coordX1 = Double.toString(coordX1);
    }

    public void setCoordY1(final String coordY1) {
        this.coordY1 = coordY1;
    }

    public void setCoordY1(final double coordY1) {
        this.coordY1 = Double.toString(coordY1);
    }

    public void setCoordZ1(final String coordZ1) {
        this.coordZ1 = coordZ1;
    }

    public void setCoordZ1(final double coordZ1) {
        this.coordZ1 = Double.toString(coordZ1);
    }

    public String getCoordX0() {
        return coordX0;
    }

    public double getCoordX0AsDouble() {
        return Double.parseDouble(coordX0);
    }

    public String getCoordY0() {
        return coordY0;
    }

    public double getCoordY0AsDouble() {
        return Double.parseDouble(coordY0);
    }

    public String getCoordZ0() {
        return coordZ0;
    }

    public double getCoordZ0AsDouble() {
        return Double.parseDouble(coordZ0);
    }

    public String getCoordX1() {
        return coordX1;
    }

    public double getCoordX1AsDouble() {
        return Double.parseDouble(coordX1);
    }

    public String getCoordY1() {
        return coordY1;
    }

    public double getCoordY1AsDouble() {
        return Double.parseDouble(coordY1);
    }

    public String getCoordZ1() {
        return coordZ1;
    }

    public double getCoordZ1AsDouble() {
        return Double.parseDouble(coordZ1);
    }

    public String getOutputFormat() {
        return "Operation: %1$s; "
                + "Status: %2$s; "
                + "First vector: (%3$s, %4$s, %5$s); "
                + "Second vector: (%6$s, %7$s, %8$s); "
                + "Result: %9$s.";
    }

    public void compute(final Vector3Operation operation) {
        switch (operation) {
            case GET_NORM_FIRST_VECTOR:     getNormOfFirstVector();
                                            break;

            case GET_NORM_SECOND_VECTOR:    getNormOfSecondVector();
                                            break;

            case NORMAlIZE_FIRST_VECTOR:    normalizeFirstVector();
                                            break;

            case NORMALIZE_SECOND_VECTOR:   normalizeSecondVector();
                                            break;

            case CALCULATE_DOT_PRODUCT:     getDotProduct();
                                            break;

            case CALCULATE_CROSS_PRODUCT:   getCrossProduct();
                                            break;

            default: break;
        }

        logger.pushMessage(getOutputFormat(), operation.toString(), getStatus(),
                           coordX0, coordY0, coordZ0,
                           coordX1, coordY1, coordZ1,
                           resultOfLastAction);
    }

    public String getResultOfLastAction() {
        return resultOfLastAction;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    private void getNormOfFirstVector() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatFirstVector()) {
            return;
        }

        Vector3 vector = getFirstVector();

        resultOfLastAction = Double.toString(vector.getNorm());
    }

    private void getNormOfSecondVector() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatSecondVector()) {
            return;
        }

        Vector3 vector = getSecondVector();

        resultOfLastAction = Double.toString(vector.getNorm());
    }

    private void normalizeFirstVector() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatFirstVector()) {
            return;
        }

        Vector3 vector = getFirstVector();

        try {
            vector.normalize();
        } catch (ArithmeticException e) {
            status = Vector3ViewModelStatus.FIRST_VECTOR_SMALL_NUMBERS;
            return;
        }

        resultOfLastAction = vector.toString();

        setComponentsOfFirstVector(vector);
    }

    private void normalizeSecondVector() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatSecondVector()) {
            return;
        }

        Vector3 vector = getSecondVector();

        try {
            vector.normalize();
        } catch (ArithmeticException e) {
            status = Vector3ViewModelStatus.SECOND_VECTOR_SMALL_NUMBERS;
            return;
        }

        resultOfLastAction = vector.toString();

        setComponentsOfSecondVector(vector);
    }

    private void getDotProduct() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatBothVectors()) {
            return;
        }

        Vector3 firstVector  = getFirstVector();
        Vector3 secondVector = getSecondVector();

        resultOfLastAction = Double.toString(firstVector.dot(secondVector));
    }

    private void getCrossProduct() {
        clearResult();
        resetStatus();

        if (!checkCorrectFormatBothVectors()) {
            return;
        }

        Vector3 firstVector  = getFirstVector();
        Vector3 secondVector = getSecondVector();
        Vector3 resultVector = firstVector.cross(secondVector);

        if (Double.isNaN(resultVector.x())) {
            status = Vector3ViewModelStatus.COPLANAR_VECTORS;
            return;
        }

        resultOfLastAction = firstVector.cross(secondVector).toString();
    }

    private Vector3 getFirstVector() {
        return new Vector3(Double.parseDouble(coordX0),
                           Double.parseDouble(coordY0),
                           Double.parseDouble(coordZ0));
    }

    private Vector3 getSecondVector() {
        return new Vector3(Double.parseDouble(coordX1),
                           Double.parseDouble(coordY1),
                           Double.parseDouble(coordZ1));
    }

    private void setComponentsOfFirstVector(final Vector3 vector) {
        coordX0 = formatter.format(vector.x());
        coordY0 = formatter.format(vector.y());
        coordZ0 = formatter.format(vector.z());
    }

    private void setComponentsOfSecondVector(final Vector3 vector) {
        coordX1 = formatter.format(vector.x());
        coordY1 = formatter.format(vector.y());
        coordZ1 = formatter.format(vector.z());
    }

    private boolean checkCorrectFormatFirstVector() {
        try {
            Double.valueOf(coordX0);
            Double.valueOf(coordY0);
            Double.valueOf(coordZ0);
        } catch (NumberFormatException e) {
            status = Vector3ViewModelStatus.FIRST_VECTOR_WRONG_FORMAT;
        }

        return status.equals(Vector3ViewModelStatus.OK);
    }

    private boolean checkCorrectFormatSecondVector() {
        try {
            Double.valueOf(coordX1);
            Double.valueOf(coordY1);
            Double.valueOf(coordZ1);
        } catch (NumberFormatException e) {
            status = Vector3ViewModelStatus.SECOND_VECTOR_WRONG_FORMAT;
        }

        return status.equals(Vector3ViewModelStatus.OK);
    }

    private boolean checkCorrectFormatBothVectors() {
        final boolean firstResult = checkCorrectFormatFirstVector();
        final boolean secondResult = checkCorrectFormatSecondVector();

        return  firstResult && secondResult;
    }

    private void clearResult() {
        resultOfLastAction = "";
    }

    private void resetStatus() {
        status = Vector3ViewModelStatus.OK;
    }

}
