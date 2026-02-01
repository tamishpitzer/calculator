package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class UnaryOpNode extends ASTNode {
    private final TokenType operator;      
    private final ASTNode operand;
    private final boolean isPrefix;

    public UnaryOpNode(TokenType operator, ASTNode operand, boolean isPrefix) {
        this.operator = operator;
        this.operand = operand;
        this.isPrefix = isPrefix;
    }

    @Override
    public Long evaluate(VariableStore store) throws Exception {
        if (!(operand instanceof VariableNode)) {
            throw new Exception("Increment/Decrement can only be applied to variables");
        }

        VariableNode varNode = (VariableNode) operand;
        String varName = varNode.getName();
        Long currentVal = store.get(varName);

        if (currentVal == null) {
            currentVal = 0L;
        }

        long oldValue = currentVal;
        long newValue = operator == TokenType.INCREMENT ? oldValue + 1 : oldValue - 1;

        store.set(varName, newValue);

        return isPrefix ? newValue : oldValue;
    }
}
