package com.example.algorithm.stack;

import com.example.algorithm.Debug;
import junit.framework.TestCase;

import java.util.Arrays;

public class PostfixExpressionTest extends TestCase {
    public void testBoundary() throws Exception {
        PostfixExpression expression = new PostfixExpression(new String[0]);
        assertTrue(Arrays.equals(expression.generate(), new String[0]));
    }

    public void testGenOneOperator() throws Exception {
        PostfixExpression expression = new PostfixExpression(new String[]{"3.6"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3.6"}));

        expression = new PostfixExpression(new String[]{"3.6", "+", "5.7"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3.6", "5.7", "+"}));

        expression = new PostfixExpression(new String[]{"3", "-", "2.3"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3", "2.3", "-"}));

        expression = new PostfixExpression(new String[]{"4", "*", "5"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(new String[]{"4", "/", "5"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "/"}));
    }

    public void testGenTwoOperator() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"3.6", "+", "5.7", "-", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "+", "2", "-"}));

        expression = new PostfixExpression(
                new String[]{"3.6", "*", "5.7", "-", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "*", "2", "-"}));

        expression = new PostfixExpression(
                new String[]{"3.6", "+", "5.7", "*", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "2", "*", "+"}));
    }

    public void testGenComplex() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"9", "+", "2", "*", "3", "-", "10", "/", "2"});
        // Debug.logTest(Arrays.toString(expression.getInfixExprTokens()));
        // Debug.logTest(Arrays.toString(expression.generate()));
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"9", "2", "3", "*", "+", "10", "2", "/", "-"}));

        expression = new PostfixExpression(
                new String[]{"9", "+", "2", "*", "3", "*", "4", "-", "3"});
        // Debug.logTest(Arrays.toString(expression.generate()));
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"9", "2", "3", "*", "4", "*", "+", "3", "-"}));
    }

    public void testEvlOneOperator() throws Exception {
        PostfixExpression expression = new PostfixExpression(new String[]{"3"});
        assertEquals(expression.evaluate(), 3.0);

        expression = new PostfixExpression(new String[]{"3", "+", "5"});
        assertEquals(expression.evaluate(), 8.0);

        expression = new PostfixExpression(new String[]{"3", "-", "2.0"});
        assertEquals(expression.evaluate(), 1.0);

        expression = new PostfixExpression(new String[]{"4", "*", "5"});
        assertEquals(expression.evaluate(), 20.0);

        expression = new PostfixExpression(new String[]{"15", "/", "5"});
        assertEquals(expression.evaluate(), 3.0);
    }

    public void testEvlTwoOperator() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"3", "+", "5", "-", "2"});
        assertEquals(expression.evaluate(), 6.0);

        expression = new PostfixExpression(
                new String[]{"3", "*", "5", "-", "2"});
        assertEquals(expression.evaluate(), 13.0);

        expression = new PostfixExpression(
                new String[]{"3", "+", "5", "*", "8"});
        assertEquals(expression.evaluate(), 43.0);
    }

    public void testEvlComplex() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"9", "+", "2", "*", "3", "-", "10", "/", "2"});
        assertEquals(expression.evaluate(), 10.0);

        expression = new PostfixExpression(
                new String[]{"9", "+", "2", "*", "3", "*", "4", "-", "3"});
        assertEquals(expression.evaluate(), 30.0);
        // Debug.logTest(Arrays.toString(expression.generate()));
    }

    public void testBracket() throws Exception {
        // Debug.setVerboseDebugLog(true);
        PostfixExpression expression = new PostfixExpression(
                new String[]{"(", "3.6", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3.6"}));

        expression = new PostfixExpression(
                new String[]{"(", "(", "(", "3.6", ")", ")", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3.6"}));
    }

    public void testBracketOneOperator() throws Exception {
        // Debug.setVerboseDebugLog(true);
        PostfixExpression expression = new PostfixExpression(
                new String[]{"(","3.6", "+", "5.7", ")"});
        Debug.logVerbose(Arrays.toString(expression.getInfixExprTokens()));
        Debug.logVerbose(Arrays.toString(expression.generate()));
        assertTrue(Arrays.equals(expression.generate(), new String[]{"3.6", "5.7", "+"}));

        expression = new PostfixExpression(new String[]{"(", "4", "*", "5", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(
                new String[]{"(", "(", "(", "(", "4", "*", "5", ")", ")", ")", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(new String[]{"(", "4", ")", "*", "5"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(new String[]{"4", "*", "(", "5", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(new String[]{"(", "4", ")", "*", "(", "5", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));

        expression = new PostfixExpression(
                new String[]{"(", "(", "(", "4", ")", ")", ")", "*",
                        "(", "(", "5", ")", ")"});
        assertTrue(Arrays.equals(expression.generate(), new String[]{"4", "5", "*"}));
    }

    public void testBracketTwoOperator() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"(", "3.6", "+", "5.7",")", "-", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "+", "2", "-"}));

        expression = new PostfixExpression(
                new String[]{"3.6", "+", "(", "5.7", "-", "2", ")"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "2", "-", "+"}));

        expression = new PostfixExpression(
                new String[]{"(", "3.6", "*", "5.7",")" ,"-", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "*", "2", "-"}));

        expression = new PostfixExpression(
                new String[]{"3.6", "*", "(", "5.7", "-", "2", ")"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "2", "-", "*"}));

        expression = new PostfixExpression(
                new String[]{"3.6", "+", "(", "5.7", "*", "2", ")"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "2", "*", "+"}));

         expression = new PostfixExpression(
                new String[]{"(", "3.6", "+", "5.7", ")", "*", "2"});
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"3.6", "5.7", "+", "2", "*"}));
    }

    public void testBracketComplex() throws Exception {
        PostfixExpression expression = new PostfixExpression(
                new String[]{"9", "+", "(", "3", "-", "1", ")", "*", "3", "-", "10", "/", "2"});
        // Debug.logTest(Arrays.toString(expression.getInfixExprTokens()));
        // Debug.logTest(Arrays.toString(expression.generate()));
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"9", "3", "1", "-", "3", "*", "+", "10", "2", "/", "-"}));

        expression = new PostfixExpression(
                new String[]{"9", "+", "(", "3", "-", "1", ")", "*", "3", "*", "4", "-", "3"});
        // Debug.logTest(Arrays.toString(expression.generate()));
        assertTrue(Arrays.equals(expression.generate(),
                new String[]{"9", "3", "1", "-", "3", "*", "4", "*", "+", "3", "-"}));
    }
}
