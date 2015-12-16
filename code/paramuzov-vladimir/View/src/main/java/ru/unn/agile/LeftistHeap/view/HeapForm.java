package ru.unn.agile.LeftistHeap.view;

import ru.unn.agile.LeftistHeap.infrastructure.LeftistHeapTxtLogger;
import ru.unn.agile.LeftistHeap.viewmodel.LeftistHeapViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public final class HeapForm {
    private static final String LOG_FILENAME = "./paramuzov-vladimir-log.log";
    private final LeftistHeapViewModel viewModel;
    private JPanel rootPanel;
    private JTextField keyValueTextField;
    private JButton applyOperationButton;
    private JTextArea errorMessageTextField;
    private JTextArea heapContentTextField;
    private JComboBox<LeftistHeapViewModel.Operations> operationComboBox;
    private JList<String> logList;

    private HeapForm() {
        LeftistHeapTxtLogger txtLogger = new LeftistHeapTxtLogger(LOG_FILENAME);
        viewModel = new LeftistHeapViewModel(txtLogger);

        operationComboBox.setModel(new JComboBox<>(
                LeftistHeapViewModel.Operations.values()).getModel());
        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBind();
                bind();
            }
        });

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBind();
                viewModel.applyOperation();
                bind();
            }
        };
        applyOperationButton.addActionListener(buttonListener);

        DocumentListener fieldsListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }
        };

        keyValueTextField.getDocument().addDocumentListener(fieldsListener);
        keyValueTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                backBind();
                viewModel.valueFieldFocusLost();
                bind();
            }
        });
        errorMessageTextField.setForeground(Color.RED);

        bind();
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("HeapForm");
        frame.setContentPane(new HeapForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void backBind() {
        viewModel.setKeyValue(keyValueTextField.getText());
        viewModel.setOperation((LeftistHeapViewModel.Operations)
                                operationComboBox.getSelectedItem());
    }

    private void bind() {
        applyOperationButton.setEnabled(viewModel.isApplyButtonEnabled());
        heapContentTextField.setText(viewModel.getHeapContent());
        errorMessageTextField.setText(viewModel.getErrorText());

        List<String> log = viewModel.getLogger().getLog();
        logList.setListData(log.toArray(new String[log.size()]));
        logList.ensureIndexIsVisible(log.size() - 1);
    }
}
