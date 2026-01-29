package com.example.parser;

import com.example.store.VariableStore;

public class VariableNode extends ASTNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Long evaluate(VariableStore store) throws EvaluatorException {
        Long value = store.get(name);
        if (value == null) {
            throw new EvaluatorException("Undefined variable: " + name);
        }
        return value;
    }
}
