package com.example.parser;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.example.lexer.Lexer;

public class ParserTest {

    @Test
    public void testParserLiteral() throws Exception {
        Lexer lexer = new Lexer("42");
        Parser parser = new Parser(lexer.tokenize());
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof LiteralNode);
    }

    @Test
    public void testParserVariable() throws Exception {
        Lexer lexer = new Lexer("x");
        Parser parser = new Parser(lexer.tokenize());
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof VariableNode);
    }

    @Test
    public void testParserBinaryOp() throws Exception {
        Lexer lexer = new Lexer("2 + 3");
        Parser parser = new Parser(lexer.tokenize());
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof BinaryOpNode);
    }

    @Test
    public void testParserAssignment() throws Exception {
        Lexer lexer = new Lexer("x = 5");
        Parser parser = new Parser(lexer.tokenize());
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof AssignmentNode);
    }
}
