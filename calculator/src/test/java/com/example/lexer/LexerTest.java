package com.example.lexer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LexerTest {

    @Test
    public void testLexerNumbers() throws Exception {
        Lexer lexer = new Lexer("42 123 0");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("42", tokens.get(0).getValue());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals("123", tokens.get(1).getValue());
    }

    @Test
    public void testLexerIdentifiers() throws Exception {
        Lexer lexer = new Lexer("x y counter _var");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("x", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("counter", tokens.get(2).getValue());
    }

    @Test
    public void testLexerOperators() throws Exception {
        Lexer lexer = new Lexer("+ - * / ++ -- += -= *= /=");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS, tokens.get(1).getType());
        assertEquals(TokenType.MULTIPLY, tokens.get(2).getType());
        assertEquals(TokenType.DIVIDE, tokens.get(3).getType());
        assertEquals(TokenType.INCREMENT, tokens.get(4).getType());
        assertEquals(TokenType.DECREMENT, tokens.get(5).getType());
        assertEquals(TokenType.PLUS_ASSIGN, tokens.get(6).getType());
    }

    @Test
    public void testLexerParentheses() throws Exception {
        Lexer lexer = new Lexer("( )");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).getType());
    }
}
