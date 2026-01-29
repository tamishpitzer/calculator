package com.example.parser;

import com.example.store.VariableStore;

public abstract class ASTNode {
    public abstract long evaluate(VariableStore store) throws EvaluatorException;

    public static class EvaluatorException extends Exception {
        public EvaluatorException(String message) {
            super(message);
        }

        public EvaluatorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
