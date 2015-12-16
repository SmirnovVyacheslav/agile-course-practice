package ru.unn.agile.Vec3.ViewModel;

public enum Vector3Operation {
    GET_NORM_FIRST_VECTOR("Get norm first vector"),
    GET_NORM_SECOND_VECTOR("Get norm second vector"),
    NORMAlIZE_FIRST_VECTOR("Normalize first vector"),
    NORMALIZE_SECOND_VECTOR("Normalize second vector"),
    CALCULATE_DOT_PRODUCT("Calculate dot product"),
    CALCULATE_CROSS_PRODUCT("Calculate cross product");

    private final String actionName;

    Vector3Operation(final String actionName) {
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return actionName;
    }
}
