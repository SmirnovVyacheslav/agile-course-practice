package ru.unn.agile.MergeSort.View;

import ru.unn.agile.MergeSort.Infrastructure.MergeSortFileLogger;
import ru.unn.agile.MergeSort.ViewModel.MergeSortViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public final class MergeSortView {
    private final MergeSortViewModel viewModel;
    private JPanel mainPanel;
    private JTextField sourceArrayTextField;
    private JTextField sortedArrayTextField;
    private JButton sortButton;
    private JTextField statusTextField;
    private JComboBox<MergeSortViewModel.SortingOrder> sortingOrderComboBox;
    private JList<String> logRecordsList;
    private static final String LOG_OUTPUT_FILE_NAME = "./MergeSortViewLog.log";

    public static void main(final String[] args) {
        JFrame frame = new JFrame("MergeSortView");

        MergeSortFileLogger fileLogger = new MergeSortFileLogger(LOG_OUTPUT_FILE_NAME);
        frame.setContentPane(new MergeSortView(new MergeSortViewModel(fileLogger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private MergeSortView(final MergeSortViewModel viewModel) {
        this.viewModel = viewModel;

        loadListOfSortingOrders();
        sortingOrderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mergeSortViewBackBind();
                mergeSortViewBind();
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mergeSortViewBackBind();
                viewModel.sort();
                mergeSortViewBind();
            }
        });

        sourceArrayTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                mergeSortViewBackBind();
                mergeSortViewBind();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                mergeSortViewBackBind();
                mergeSortViewBind();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                mergeSortViewBackBind();
                mergeSortViewBind();
            }
        });

        mergeSortViewBind();
    }

    private void mergeSortViewBackBind() {
        viewModel.setSortingArray(sourceArrayTextField.getText());
        viewModel.setSortingOrder((MergeSortViewModel.SortingOrder)
                                  sortingOrderComboBox.getSelectedItem());
    }

    private void mergeSortViewBind() {
        sortedArrayTextField.setText(viewModel.getResultArray());
        statusTextField.setText(viewModel.getSortingArrayStatus());

        sortButton.setEnabled(viewModel.isSortButtonEnabled());

        List<String> recordsList = viewModel.getLogger().getRecordsList();
        logRecordsList.setListData(recordsList.toArray(new String[recordsList.size()]));
        logRecordsList.ensureIndexIsVisible(recordsList.size() - 1);
    }

    private void loadListOfSortingOrders() {
        MergeSortViewModel.SortingOrder[] sortingOrder = MergeSortViewModel.SortingOrder.values();
        sortingOrderComboBox.setModel(new JComboBox<>(sortingOrder).getModel());
    }
}
