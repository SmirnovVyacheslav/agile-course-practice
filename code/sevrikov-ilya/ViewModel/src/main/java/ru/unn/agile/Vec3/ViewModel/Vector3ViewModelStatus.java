package ru.unn.agile.Vec3.ViewModel;

public final class Vector3ViewModelStatus {
    public static final String OK = "OK";
    public static final String FIRST_VECTOR_WRONG_FORMAT =
            "Components of first vector have wrong format.";
    public static final String SECOND_VECTOR_WRONG_FORMAT =
            "Components of second vector have wrong format.";
    public static final String FIRST_VECTOR_SMALL_NUMBERS =
            "Components of first vector are small";
    public static final String SECOND_VECTOR_SMALL_NUMBERS =
            "Components of second vector are small";
    public static final String COPLANAR_VECTORS = "Vectors are coplanar.";
    public static final String LOGGER_CANNOT_BE_NULL = "Logger can't be null.";

    private Vector3ViewModelStatus() { }
}
