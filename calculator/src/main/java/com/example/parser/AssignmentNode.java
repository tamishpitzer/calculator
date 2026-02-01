package com.example.parser;

import com.example.lexer.TokenType;
import com.example.store.VariableStore;

public class AssignmentNode extends ASTNode {
    private final String variableName;
    private final TokenType assignmentOp;  // ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, etc.
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

        Operator op = Operator.from(assignmentOp);
        if (op == null) {
            throw new Exception("Unknown assignment operator: " + assignmentOp);
        }

        long result = op.apply(currentVal, rightVal);
        store.set(variableName, result);
        return result;
    }
}
