package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class UnaryOpNode extends ASTNode {
    private final TokenType operator;      // INCREMENT or DECREMENT
    private final ASTNode operand;
    private final boolean isPrefix;        // true for ++i, false for i++

    public UnaryOpNode(TokenType operator, ASTNode operand, boolean isPrefix) {
        this.operator = operator;
        this.operand = operand;
        this.isPrefix = isPrefix;
    }

    @Override
    public Long evaluate(VariableStore store) throws EvaluatorException {
        if (!(operand instanceof VariableNode)) {
            throw new EvaluatorException("Increment/Decrement can only be applied to variables");
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
