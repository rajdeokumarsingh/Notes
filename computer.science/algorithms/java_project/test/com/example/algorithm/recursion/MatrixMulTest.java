package com.example.algorithm.recursion;

import Jama.Matrix;
import com.example.algorithm.Debug;
import junit.framework.TestCase;

import java.util.Arrays;

public class MatrixMulTest extends TestCase {

    public void testMul() throws Exception {
        double[][] x = new double[][]{{1, 2}, {1, 4}, {7, 1}};
        double[][] y = new double[][]{{1, 2, 1}, {1, 5, 1}};
        double[][] result = MatrixMul.mul(x, y);

        assertEquals(result.length, 3);
        assertEquals(result[0].length, 3);

        Matrix matrixX = new Matrix(x);
        Matrix matrixY = new Matrix(y);
        Matrix matrixMul = matrixX.times(matrixY);
        double[][] test = matrixMul.getArray();
        for (int i = 0; i < test.length; i++) {
            assertTrue(Arrays.equals(test[i], result[i]));
            Debug.logTest(Arrays.toString(test[i]));
        }
    }
}
