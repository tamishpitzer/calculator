package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class BinaryOpNode implements ASTNode {
    private final ASTNode left;
    private final TokenType operator;
    private final ASTNode right;

    public BinaryOpNode(ASTNode left, TokenType operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Long evaluate(VariableStore store) throws Exception {
        long leftVal = left.evaluate(store);
        long rightVal = right.evaluate(store);

        return Operator.from(operator)
                       .orElseThrow(() -> new Exception("Unknown binary operator: " + operator))
                       .apply(leftVal, rightVal);
    }
}
