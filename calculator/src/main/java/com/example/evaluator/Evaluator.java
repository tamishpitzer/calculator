package com.example.evaluator;

import java.util.List;

import com.example.lexer.Lexer;
import com.example.lexer.Lexer.LexerException;
import com.example.lexer.Token;
import com.example.parser.ASTNode;
import com.example.parser.Parser;
import com.example.parser.Parser.ParserException;
import com.example.store.VariableStore;

public class Evaluator {
    private final VariableStore variableStore;

    public Evaluator() {
        this.variableStore = new VariableStore();
    }

    public void evaluateExpression(String expression) throws LexerException, ParserException, ASTNode.EvaluatorException {
        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        ast.evaluate(variableStore);
    }

    public VariableStore getVariableStore() {
        return variableStore;
    }

    public void reset() {
        variableStore.clear();
    }
}
