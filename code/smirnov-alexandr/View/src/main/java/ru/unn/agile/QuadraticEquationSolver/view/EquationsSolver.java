package ru.unn.agile.QuadraticEquationSolver.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.QuadraticEquationSolver.viewmodel.ViewModel;

public class EquationsSolver {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField textFieldCoeffA;
    @FXML
    private TextField textFieldCoeffB;
    @FXML
    private TextField textFieldCoeffC;
    @FXML
    private Button buttonSolveEquation;

    @FXML
    void initialize() {
        textFieldCoeffA.textProperty().bindBidirectional(viewModel.coeffAProperty());
        textFieldCoeffB.textProperty().bindBidirectional(viewModel.coeffBProperty());
        textFieldCoeffC.textProperty().bindBidirectional(viewModel.coeffCProperty());
        buttonSolveEquation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.solveQuadraticEquation();
            }
        });
    }
}
