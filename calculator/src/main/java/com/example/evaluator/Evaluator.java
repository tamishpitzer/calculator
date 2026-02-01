package com.example.evaluator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.lexer.Token;
import com.example.parser.ASTNode;
import com.example.parser.Parser;
import com.example.store.VariableStore;
import com.example.util.LexerUtil;

@Component
public class Evaluator {
    
    private final VariableStore variableStore;

    public Evaluator(VariableStore variableStore) {
        this.variableStore = variableStore;
    }

    public void evaluateExpression(String expression) throws Exception{
        List<Token> tokens = LexerUtil.tokenize(expression);

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        ast.evaluate(variableStore);
    }

    public VariableStore getVariableStore() {
        return variableStore;
    }
}
