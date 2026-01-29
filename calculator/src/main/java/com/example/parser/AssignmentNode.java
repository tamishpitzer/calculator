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
    public long evaluate(VariableStore store) throws EvaluatorException {
        long rightVal = value.evaluate(store);

        if (assignmentOp == TokenType.ASSIGN) {
            store.set(variableName, rightVal);
            return rightVal;
        }

        Long currentVal = store.get(variableName);
        if (currentVal == null) {
            currentVal = 0L;
        }

        long result;
        switch (assignmentOp) {
            case PLUS_ASSIGN:
                result = currentVal + rightVal;
                break;
            case MINUS_ASSIGN:
                result = currentVal - rightVal;
                break;
            case MULTIPLY_ASSIGN:
                result = currentVal * rightVal;
                break;
            case DIVIDE_ASSIGN:
                if (rightVal == 0) {
                    throw new EvaluatorException("Division by zero");
                }
                result = currentVal / rightVal;
                break;
            default:
                throw new EvaluatorException("Unknown assignment operator: " + assignmentOp);
        }

        store.set(variableName, result);
        return result;
    }
}
