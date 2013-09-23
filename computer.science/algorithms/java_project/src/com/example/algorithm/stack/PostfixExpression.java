package com.example.algorithm.stack;

import com.example.algorithm.Debug;

import java.util.ArrayList;
import java.util.Stack;

public class PostfixExpression {
    private String[] infixExprTokens;

    /**
     * Create a postfix expression from an infix expression
     * @param infixExpr infix expression
     */
    public PostfixExpression(String infixExpr) {
        // TODO: need a tokenizer to parse the expression
        throw new UnsupportedOperationException();
    }

    /**
     * Create a postfix expression from an infix expression
     * @param infixExpr infixExprTokens of a infix expression
     */
    public PostfixExpression(String[] infixExpr) {
        // TODO: check the grammar
        infixExprTokens = infixExpr;
    }

    /**
     * Evaluate the postfix expression
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

    private double evaluate(double v1, double v2, String op) {
        assert ("+".equals(op) || "-".equals(op)
                || "*".equals(op) || "/".equals(op));

        if("+".equals(op)) return v1 + v2;
        if("-".equals(op)) return v1 - v2;
        if("*".equals(op)) return v1 * v2;
        if("/".equals(op)) return v1 / v2;

        throw new IllegalStateException();
    }

    /**
     * Generate postfix expression from infix expression
     * @return postfix expression
     */
    public String[] generate() {
        ArrayList<String> postfix = new ArrayList<String>();
        Stack<String> operators = new Stack<String>();
        for (String token : infixExprTokens) {
            if(isOpenParenthesis(token)) {
                Debug.logVerbose("push: " + token);
                operators.push(token);
                continue;
            }
            if (isCloseParenthesis(token)) {
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
                if (operators.empty() || isOpenParenthesis(operators.peek())) {
                    Debug.logVerbose("push 1: " + token);
                    operators.push(token);
                    continue;
                }
                // operator not empty and not "("
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

    private boolean isOpenParenthesis(String token) {
        return "(".equals(token);
    }

    private boolean isCloseParenthesis(String token) {
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
                || "*".equals(op) || "/".equals(op));

        if ("+".equals(op) || "-".equals(op)) return 0;
        if ("*".equals(op) || "/".equals(op)) return 1;

        throw new IllegalStateException();
    }

    public String[] getInfixExprTokens() {
        return infixExprTokens;
    }
}
