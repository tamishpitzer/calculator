package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class BinaryOpNode extends ASTNode {
    private final ASTNode left;
    private final TokenType operator;
    private final ASTNode right;

    public BinaryOpNode(ASTNode left, TokenType operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public long evaluate(VariableStore store) throws EvaluatorException {
        long leftVal = left.evaluate(store);
        long rightVal = right.evaluate(store);

        switch (operator) {
            case PLUS:
                return leftVal + rightVal;
            case MINUS:
                return leftVal - rightVal;
            case MULTIPLY:
                return leftVal * rightVal;
            case DIVIDE:
                if (rightVal == 0) {
                    throw new EvaluatorException("Division by zero");
                }
                return leftVal / rightVal;
            default:
                throw new EvaluatorException("Unknown binary operator: " + operator);
        }
    }
}
