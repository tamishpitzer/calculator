package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class AssignmentNode implements ASTNode {
    private final String variableName;
    private final TokenType assignmentOp;
    private final ASTNode value;

    public AssignmentNode(String variableName, TokenType assignmentOp, ASTNode value) {
        this.variableName = variableName;
        this.assignmentOp = assignmentOp;
        this.value = value;
    }

    @Override
    public Long evaluate(VariableStore store) throws Exception {
        Long rightVal = value.evaluate(store);

        if (assignmentOp == TokenType.ASSIGN) {
            store.set(variableName, rightVal);
            return rightVal;
        }

        Long currentVal = store.get(variableName);
        if (currentVal == null) {
            currentVal = 0L;
        }

        long result = Operator.from(assignmentOp)
                .orElseThrow(() -> new Exception("Unknown assignment operator: " + assignmentOp))
                .apply(currentVal, rightVal);

        store.set(variableName, result);
        return result;
    }
}
