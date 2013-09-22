package com.example.algorithm.stack;

import com.example.algorithm.Debug;

import java.util.ArrayList;
import java.util.Stack;

public class PostfixExpression {
    private String[] tokens;

    public String[] getTokens() {
        return tokens;
    }

    /**
     * Create a postfix expression
     * @param expression
     */
    public PostfixExpression(String expression) {
        // todo, need a tokenizer to parse the expression
    }

    /**
     * Create a postfix expression
     * @param expression tokens
     */
    public PostfixExpression(String[] expression) {
        // todo, need a lexer to check the grammar
        tokens = expression;
    }

    /**
     * Evaluate the expression
     * @return value of the expression
     */
    public double evaluate() {
        String[] postfixes = generate();
        Stack<Double> values = new Stack<Double>();
        for (String postfix : postfixes) {
            if (isNumber(postfix)) {
                values.push(Double.parseDouble(postfix));
                continue;
            }
            if (isOperator(postfix)) {
                double v1 = values.pop();
                double v2 = values.pop();
                values.push(evaluate(v2, v1, postfix));
            }
        }
        return values.pop();
    }

    private double evaluate(double v1, double v2, String postfix) {
        assert ("+".equals(postfix) || "-".equals(postfix)
                || "*".equals(postfix) || "/".equals(postfix));

        if("+".equals(postfix)) return v1 + v2;
        if("-".equals(postfix)) return v1 - v2;
        if("*".equals(postfix)) return v1 * v2;
        if("/".equals(postfix)) return v1 / v2;

        throw new IllegalStateException();
    }

    /**
     * Generate postfix expression
     * @return postfix expression
     */
    public String[] generate() {
        ArrayList<String> postfix = new ArrayList<String>();
        Stack<String> operators = new Stack<String>();
        for (String token : tokens) {
            if(isOpenParen(token)) {
                Debug.logVerbose("push: " + token);
                operators.push(token);
                continue;
            }
            if (isCloseParen(token)) {
                String op = null;
                while ((op = operators.pop()) != "(") {
                    Debug.logVerbose("add: " + op);
                    postfix.add(op);
                }
                continue;
            }
            if (isNumber(token)) {
                Debug.logVerbose("add: " + token);
                postfix.add(token);
                continue;
            }
            if (isOperator(token)) {
                if (operators.empty() || "(".equals(operators.peek())) {
                    Debug.logVerbose("push 1: " + token);
                    operators.push(token);
                    continue;
                }
                // operator not empty
                while (!operators.empty()) {
                    if (operatorPriority(token) > operatorPriority(operators.peek())) break;

                    String op = operators.pop();
                    postfix.add(op);
                    Debug.logVerbose("add 1: " + op);
                }
                operators.push(token);
                Debug.logVerbose("push 2: " + token);
            }
        }
        while (!operators.empty()) {
            String op = operators.pop();
            postfix.add(op);
            Debug.logVerbose("add: " + op);
        }

        String[] ret = new String[postfix.size()];
        postfix.toArray(ret);
        return ret;
    }

    private boolean isOpenParen(String token) {
        return "(".equals(token);
    }

    private boolean isCloseParen(String token) {
        return ")".equals(token);
    }

    private boolean isNumber(String token) {
        boolean ret = true;
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    private boolean isOperator(String token) {
        if ("+".equals(token) || "-".equals(token)
                || "*".equals(token) || "/".equals(token)) {
            return true;
        }
        return false;
    }

    private int operatorPriority(String op) {
        assert ("+".equals(op) || "-".equals(op)
                || "*".equals(op) || "/".equals(op)
                || "(".equals(op));

        if ("+".equals(op) || "-".equals(op)) return 0;
        if ("*".equals(op) || "/".equals(op)) return 1;
        if ("(".equals(op)) return -1;

        throw new IllegalStateException();
    }
}
