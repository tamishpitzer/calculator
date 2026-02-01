package com.example.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.util.LexerUtil;

class ParserTest {

    @Test
    void testParserLiteral() throws Exception {
        Parser parser = new Parser(LexerUtil.tokenize("42"));
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof LiteralNode);
    }

    @Test
    void testParserVariable() throws Exception {
        Parser parser = new Parser(LexerUtil.tokenize("x"));
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof VariableNode);
    }

    @Test
    void testParserBinaryOp() throws Exception {
        Parser parser = new Parser(LexerUtil.tokenize("2 + 3"));
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof BinaryOpNode);
    }

    @Test
    void testParserAssignment() throws Exception {
        Parser parser = new Parser(LexerUtil.tokenize("x = 5"));
        ASTNode ast = parser.parse();

        assertTrue(ast instanceof AssignmentNode);
    }
}
