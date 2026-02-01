package com.example.parser;

import com.example.store.VariableStore;

public class LiteralNode implements ASTNode {
    private final long value;

    public LiteralNode(long value) {
        this.value = value;
    }

    @Override
    public Long evaluate(VariableStore store) throws Exception {
        return value;
    }
}
