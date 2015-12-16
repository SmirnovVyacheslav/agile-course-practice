package ru.unn.agile.TemperatureConverter.view;

import ru.unn.agile.TemperatureConverter.infrastructure.TemperatureConverterTxtLogger;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;
import ru.unn.agile.TemperatureConverter.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public final class Converter {
    private JComboBox<TemperatureScaleName> comboBoxScales;
    private JTextField textFieldInput;
    private JTextField textFieldResult;
    private JButton buttonConvert;
    private JTextField textFieldStatus;
    private JPanel mainPanel;
    private JList<String> logList;

    private ViewModel viewModel;

    private Converter() { }

    private Converter(final ViewModel viewModel) {
        this.viewModel = viewModel;
        bindTemperatureScalesToComboBox();
        bind();

        buttonConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                viewModel.convert();
                bind();
            }
        });

        comboBoxScales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                bind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent keyEvent) {
                backBind();
                viewModel.parse();
                bind();
            }
        };
        textFieldInput.addKeyListener(keyListener);

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(final FocusEvent e) {
                backBind();
                viewModel.onInputValueFocusLost();
                bind();
            }
        };
        textFieldInput.addFocusListener(focusLostListener);

    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Converter");
        TemperatureConverterTxtLogger logger = new TemperatureConverterTxtLogger("./Converter.log");
        frame.setContentPane(new Converter(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bindTemperatureScalesToComboBox() {
        TemperatureScaleName[] scales = TemperatureScaleName.values();
        comboBoxScales.setModel(new JComboBox<>(scales).getModel());
    }

    private void backBind() {
        viewModel.setInputTemperature(textFieldInput.getText());
        viewModel.setScale((TemperatureScaleName) comboBoxScales.getSelectedItem());
    }

    private void bind() {
        comboBoxScales.setSelectedItem(viewModel.getScale());
        buttonConvert.setEnabled(viewModel.isConvertButtonEnabled());
        textFieldResult.setText(viewModel.getResultTemperature());
        textFieldStatus.setText(viewModel.getStatusName());
        buildLog();
    }

    private void buildLog() {
        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        logList.setListData(items);
    }
}
