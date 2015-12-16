package ru.unn.agile.QuadraticEquationSolver.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuadraticEquationTest {
    private final float delta = 0.0001f;

    @Test
    public void canGetExceptionFromEquationWithFirstZeroCoefficient() {
        assertEquals(getExceptionMessage(0, 2, 3), "non quadratic equation, a is equal 0");
    }

    @Test
    public void canGetExceptionFromEquationWithAllZeroCoefficients() {
        assertEquals(getExceptionMessage(0, 0, 0), "non quadratic equation, a is equal 0");
    }

    @Test
    public void canGetOneSimpleRootEqualZero() {
        float [] answer = trySolveQuadraticEquation(1, 0, 0);
        assertArrayEquals(answer, new float[]{0.0f}, delta);
    }

    @Test
    public void canGetTwoNonZeroRootsFromEquationWithSecondCoefficientEqualZero() {
        float [] answer = trySolveQuadraticEquation(1, 0, -4);
        assertArrayEquals(answer, new float[]{-2.0f, 2.0f}, delta);
    }

    @Test
    public void canGetEmptyAnswerFromEquationWithNegativeDiscriminant() {
        assertEquals(getExceptionMessage(1, 2, 3), "discriminant less than 0");
    }

    @Test
    public void canGetOneRootOfEquationWithZeroDiscriminant() {
        float [] answer = trySolveQuadraticEquation(1, -2, 1);
        assertArrayEquals(answer, new float[]{1.0f}, delta);
    }

    @Test
    public void canGetTwoSimpleRootsOfEquationWithNonZeroDiscriminant() {
        float [] answer = trySolveQuadraticEquation(1, -5, 6);
        assertArrayEquals(answer, new float[]{2.0f, 3.0f}, delta);
    }

    @Test
    public void canGetTwoRootsOfEquationWithNonZeroDiscriminant() {
        float[] answer = trySolveQuadraticEquation(2, 4, -3);
        assertArrayEquals(answer, new float[]{-2.5811f, 0.5811f}, delta);
    }

    @Test
    public void canGetTwoRootsOfEquationWithNonZeroDiscriminantAndSmallCoefficients() {
        float [] answer = trySolveQuadraticEquation(0.00000234f, 0.0000954f, 0.00000672f);
        assertArrayEquals(answer, new float[]{-40.69866f, -0.07056f}, delta);
    }

    @Test
    public void canGetTwoRootsOfEquationWithNonZeroDiscriminantAndLargeCoefficients() {
        float [] answer = trySolveQuadraticEquation(12340552.47f, 98453552.07f, 45240552.29f);
        assertArrayEquals(answer, new float[]{-7.48849f, -0.48955f}, delta);
    }

    private float [] trySolveQuadraticEquation(final float a, final float b, final float c) {
        try {
            return QuadraticEquationSolver.solve(a, b, c);
        } catch (Exception ex) {
            return null;
        }
    }

    private String getExceptionMessage(final float a, final float b, final float c) {
        try {
            QuadraticEquationSolver.solve(a, b, c);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "";
    }
}
