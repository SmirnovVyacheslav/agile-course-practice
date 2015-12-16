package ru.unn.agile.Vec3.View;

import ru.unn.agile.Vec3.ViewModel.Vector3ViewModel;
import ru.unn.agile.Vec3.ViewModel.Vector3Operation;
import ru.unn.agile.Vec3.Infrastructure.Vector3TxtLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public final class Vector3Viewer {
    private JPanel mainPanel;
    private JTextField txtCoordZ0;
    private JTextField txtCoordY0;
    private JTextField txtCoordX0;
    private JLabel lblCoordX0;
    private JLabel lblCoordY0;
    private JLabel lblCoordZ0;
    private JTextField txtCoordZ1;
    private JTextField txtCoordY1;
    private JTextField txtCoordX1;
    private JLabel lblCoordX1;
    private JLabel lblCoordY1;
    private JLabel lblCoordZ1;
    private JTextField txtResult;
    private JLabel lblResult;
    private JComboBox<Vector3Operation> cmbActionList;
    private JButton btnCalculate;
    private JTextField txtStatus;
    private JLabel lblStatus;
    private JCheckBox cbShowLog;
    private JList<String> listLog;

    private Vector3ViewModel viewModel;

    private Vector3Viewer() { }

    private Vector3Viewer(final Vector3ViewModel viewModel) {
        this.viewModel = viewModel;

        loadActionList();

        createUIActions();

        backBind();
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Vector3dViewer");

        Vector3TxtLogger logger = new Vector3TxtLogger("./view_log.txt");
        Vector3ViewModel viewModel = new Vector3ViewModel(logger);

        frame.setContentPane(new Vector3Viewer(viewModel).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIActions() {
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBind();

                viewModel.compute((Vector3Operation) cmbActionList.getSelectedItem());

                bind();
            }
        });

        cbShowLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                listLog.setVisible(cbShowLog.isSelected());
            }
        });
    }

    private void loadActionList() {
        Vector3Operation[] actions = Vector3Operation.values();
        cmbActionList.setModel(new JComboBox<>(actions).getModel());
    }

    private  void backBind() {
        viewModel.setCoordX0(txtCoordX0.getText());
        viewModel.setCoordY0(txtCoordY0.getText());
        viewModel.setCoordZ0(txtCoordZ0.getText());

        viewModel.setCoordX1(txtCoordX1.getText());
        viewModel.setCoordY1(txtCoordY1.getText());
        viewModel.setCoordZ1(txtCoordZ1.getText());
    }

    private void bind() {
        txtCoordX0.setText(viewModel.getCoordX0());
        txtCoordY0.setText(viewModel.getCoordY0());
        txtCoordZ0.setText(viewModel.getCoordZ0());

        txtCoordX1.setText(viewModel.getCoordX1());
        txtCoordY1.setText(viewModel.getCoordY1());
        txtCoordZ1.setText(viewModel.getCoordZ1());

        txtResult.setText(viewModel.getResultOfLastAction());
        txtStatus.setText(viewModel.getStatus());

        bindLog();
    }

    private void bindLog() {
        ArrayList<String> log = new ArrayList<>(viewModel.getLog());
        Collections.reverse(log);
        String[] messages = log.toArray(new String[log.size()]);
        listLog.setListData(messages);
    }
}
