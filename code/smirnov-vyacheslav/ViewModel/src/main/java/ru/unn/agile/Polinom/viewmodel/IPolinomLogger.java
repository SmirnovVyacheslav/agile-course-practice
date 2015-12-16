package ru.unn.agile.Polinom.viewmodel;

import java.util.List;

public interface IPolinomLogger {
    void log(final String s);

    List<String> getLog();
}
