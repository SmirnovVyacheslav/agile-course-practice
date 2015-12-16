package ru.unn.agile.QuadraticEquationSolver.Model;

import java.util.ArrayList;

public final class QuadraticEquationSolutionFormer {
    private QuadraticEquationSolutionFormer() {
    }

    public static float [] form(final ArrayList<Float> roots) {
        int length = roots.size();
        float [] ans = new float[length];
        for (int i = 0; i < length; i++) {
            ans[i] = roots.get(i);
        }
        return ans;
    }
}
