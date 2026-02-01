package com.example.util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.lexer.Token;
import com.example.lexer.TokenType;

class LexerUtilTest {

    @Test
    void testLexerNumbers() throws Exception {
        List<Token> tokens = LexerUtil.tokenize("42 123 0");

        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("42", tokens.get(0).getValue());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals("123", tokens.get(1).getValue());
    }

    @Test
    void testLexerIdentifiers() throws Exception {
        List<Token> tokens = LexerUtil.tokenize("x y counter _var");

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("x", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("y", tokens.get(1).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(2).getType());
        assertEquals("counter", tokens.get(2).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
        assertEquals("_var", tokens.get(3).getValue());
    }

    @Test
    void testLexerOperators() throws Exception {
        List<Token> tokens = LexerUtil.tokenize("+ - * / ++ -- += -= *= /=");

        assertEquals(TokenType.PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS, tokens.get(1).getType());
        assertEquals(TokenType.MULTIPLY, tokens.get(2).getType());
        assertEquals(TokenType.DIVIDE, tokens.get(3).getType());
        assertEquals(TokenType.INCREMENT, tokens.get(4).getType());
        assertEquals(TokenType.DECREMENT, tokens.get(5).getType());
        assertEquals(TokenType.PLUS_ASSIGN, tokens.get(6).getType());
    }

    @Test
    void testLexerParentheses() throws Exception {
        List<Token> tokens = LexerUtil.tokenize("( )");

        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).getType());
    }
}
