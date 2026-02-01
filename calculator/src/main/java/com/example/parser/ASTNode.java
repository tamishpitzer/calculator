package com.example.parser;

import com.example.store.VariableStore;

public interface ASTNode {
    Long evaluate(VariableStore store) throws Exception;
}
