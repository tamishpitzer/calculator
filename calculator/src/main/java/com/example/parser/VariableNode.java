package com.example.parser;

import com.example.store.VariableStore;

public class VariableNode implements ASTNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Long evaluate(VariableStore store) throws Exception {
        Long value = store.get(name);
        if (value == null) {
            throw new Exception("Undefined variable: " + name);
        }
        return value;
    }
}
