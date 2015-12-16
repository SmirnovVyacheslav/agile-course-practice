package ru.unn.agile.Vec3.ViewModel;

import ru.unn.agile.Vec3.Model.Vector3;

public class SetterVectorInViewModel {
    public static void setFirstVector(final Vector3 vector,
                                      final Vector3ViewModel viewModel) {
        viewModel.setCoordX0(vector.x());
        viewModel.setCoordY0(vector.y());
        viewModel.setCoordZ0(vector.z());
    }

    public static void setSecondVector(final Vector3 vector,
                                       final Vector3ViewModel viewModel) {
        viewModel.setCoordX1(vector.x());
        viewModel.setCoordY1(vector.y());
        viewModel.setCoordZ1(vector.z());
    }

    private SetterVectorInViewModel() { }
}
