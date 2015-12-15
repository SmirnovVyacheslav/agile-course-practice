package ru.unn.agile.Polinom.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.Polinom.viewmodel.PolinomViewModel;
import ru.unn.agile.Polinom.infrastructure.TxtLogger;

public class Polinom {
    @FXML
    private PolinomViewModel viewModel;
    @FXML
    private TextField firstPolinom;
    @FXML
    private TextField secondPolinom;
    @FXML
    private TextField result;
    @FXML
    private Button add;
    @FXML
    private Button substract;
    @FXML
    private Button multiply;
    @FXML
    private Button divide;
    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./Polinom-TxtLog.log"));

        firstPolinom.textProperty().bindBidirectional(viewModel.firstOperandProperty());
        secondPolinom.textProperty().bindBidirectional(viewModel.secondOperandProperty());

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.operation(PolinomViewModel.Operation.ADD);
            }
        });

        substract.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.operation(PolinomViewModel.Operation.SUBSTRACT);
            }
        });

        multiply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.operation(PolinomViewModel.Operation.MULTIPLY);
            }
        });

        divide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.operation(PolinomViewModel.Operation.DIVIDE);
            }
        });
    }
}
