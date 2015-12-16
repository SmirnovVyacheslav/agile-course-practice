package ru.unn.agile.QuadraticEquationSolver.Model;

import java.util.ArrayList;

public final class QuadraticEquationSolver {
    private static final float COEFFICIENT_DISCRIMINANT = 4.0f;
    private static final float COEFFICIENT_ROOTS = 2.0f;

    private static ArrayList<Float> roots;

    private QuadraticEquationSolver() {
    }

    public static float[] solve(final float a, final float b, final float c)  throws Exception {
        if (isQuadraticEquation(a)) {
            roots = new ArrayList<Float>();
            getSolution(a, b, c);
            if (roots.isEmpty()) {
                throw new Exception("discriminant less than 0");
            }
            return QuadraticEquationSolutionFormer.form(roots);
        }
        throw new Exception("non quadratic equation, a is equal 0");
    }

    private static void getSolution(final float a, final float b, final float c) {
        float discriminant = calcDiscriminant(a, b, c);
        if (discriminant < 0) {
            return;
        }
        if (isNumberEqualZero(discriminant)) {
            calcSolution(a, b);
            return;
        }
        calcSolution(a, b, discriminant);
    }

    private static float calcDiscriminant(final float a, final float b, final float c) {
        return b * b - COEFFICIENT_DISCRIMINANT * a * c;
    }

    private static void calcSolution(final float a, final float b) {
        roots.add((-b) / (COEFFICIENT_ROOTS * a));
    }

    private static void calcSolution(final float a, final float b, final float discriminant) {
        float discriminantSqrt = (float) Math.sqrt(discriminant);
        float divider = COEFFICIENT_ROOTS * a;
        float firstTerm = (-b) / divider;
        float secondTerm = discriminantSqrt / divider;

        float root1 = firstTerm - secondTerm;
        float root2 = firstTerm + secondTerm;
        roots.add(Math.min(root1, root2));
        roots.add(Math.max(root1, root2));
    }

    private static boolean isQuadraticEquation(final float a) {
        return !isNumberEqualZero(a);
    }

    private static boolean isNumberEqualZero(final float num) {
        if (Math.signum(num) == 0) {
            return true;
        }
        return false;
    }
}
