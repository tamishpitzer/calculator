package com.example;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.example.evaluator.Evaluator;
import com.example.lexer.Lexer;
import com.example.lexer.Token;
import com.example.lexer.TokenType;
import com.example.parser.ASTNode;
import com.example.parser.AssignmentNode;
import com.example.parser.BinaryOpNode;
import com.example.parser.LiteralNode;
import com.example.parser.Parser;
import com.example.parser.VariableNode;
import com.example.store.VariableStore;

/**
 * Comprehensive tests for the Calculator Application
 */
public class AppTest {
    private Evaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new Evaluator();
    }

    // ============= Lexer Tests =============
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

    // ============= Parser Tests =============
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

    // ============= Evaluator Tests =============
    @Test
    public void testSimpleAssignment() throws Exception {
        evaluator.evaluateExpression("i = 0");
        assertEquals(0L, (long) evaluator.getVariableStore().get("i"));
    }

    @Test
    public void testPrefixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 0");
        evaluator.evaluateExpression("j = ++i");

        assertEquals(1L, (long) evaluator.getVariableStore().get("i"));
        assertEquals(1L, (long) evaluator.getVariableStore().get("j"));
    }

    @Test
    public void testPostfixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 5");
        evaluator.evaluateExpression("j = i++");

        assertEquals(6L, (long) evaluator.getVariableStore().get("i"));
        assertEquals(5L, (long) evaluator.getVariableStore().get("j"));
    }

    @Test
    public void testArithmetic() throws Exception {
        evaluator.evaluateExpression("a = 10");
        evaluator.evaluateExpression("b = a + 5");

        assertEquals(10L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(15L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    public void testOperatorPrecedence() throws Exception {
        evaluator.evaluateExpression("y = (5 + 3) * 10");
        assertEquals(80L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    public void testCompoundAssignment() throws Exception {
        evaluator.evaluateExpression("i = 10");
        evaluator.evaluateExpression("i += 5");

        assertEquals(15L, (long) evaluator.getVariableStore().get("i"));
    }

    @Test
    public void testMultiplication() throws Exception {
        evaluator.evaluateExpression("a = 3");
        evaluator.evaluateExpression("b = a * 4");

        assertEquals(12L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    public void testDivision() throws Exception {
        evaluator.evaluateExpression("a = 20");
        evaluator.evaluateExpression("b = a / 4");

        assertEquals(5L, (long) evaluator.getVariableStore().get("b"));
    }

    // ============= Integration Tests =============
    @Test
    public void testExampleFromRequirements() throws Exception {
        evaluator.evaluateExpression("i = 0");
        evaluator.evaluateExpression("j = ++i");
        evaluator.evaluateExpression("x = i++ + 5");
        evaluator.evaluateExpression("y = (5 + 3) * 10");
        evaluator.evaluateExpression("i += y");

        assertEquals(82L, (long) evaluator.getVariableStore().get("i"));
        assertEquals(1L, (long) evaluator.getVariableStore().get("j"));
        assertEquals(6L, (long) evaluator.getVariableStore().get("x"));
        assertEquals(80L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    public void testComplexExpression() throws Exception {
        evaluator.evaluateExpression("a = 2");
        evaluator.evaluateExpression("b = 3");
        evaluator.evaluateExpression("c = a + b * 2");

        assertEquals(2L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(3L, (long) evaluator.getVariableStore().get("b"));
        assertEquals(8L, (long) evaluator.getVariableStore().get("c"));
    }

    @Test
    public void testPrefixDecrement() throws Exception {
        evaluator.evaluateExpression("x = 10");
        evaluator.evaluateExpression("y = --x");

        assertEquals(9L, (long) evaluator.getVariableStore().get("x"));
        assertEquals(9L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    public void testPostfixDecrement() throws Exception {
        evaluator.evaluateExpression("x = 10");
        evaluator.evaluateExpression("y = x--");

        assertEquals(9L, (long) evaluator.getVariableStore().get("x"));
        assertEquals(10L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    public void testSubtraction() throws Exception {
        evaluator.evaluateExpression("a = 10");
        evaluator.evaluateExpression("b = a - 3");

        assertEquals(7L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    public void testMinusAssign() throws Exception {
        evaluator.evaluateExpression("x = 20");
        evaluator.evaluateExpression("x -= 5");

        assertEquals(15L, (long) evaluator.getVariableStore().get("x"));
    }

    @Test
    public void testMultiplyAssign() throws Exception {
        evaluator.evaluateExpression("x = 4");
        evaluator.evaluateExpression("x *= 3");

        assertEquals(12L, (long) evaluator.getVariableStore().get("x"));
    }

    @Test
    public void testDivideAssign() throws Exception {
        evaluator.evaluateExpression("x = 20");
        evaluator.evaluateExpression("x /= 4");

        assertEquals(5L, (long) evaluator.getVariableStore().get("x"));
    }

    @Test
    public void testParenthesesPrecedence() throws Exception {
        evaluator.evaluateExpression("a = 2 + 3 * 4");
        evaluator.evaluateExpression("b = (2 + 3) * 4");

        assertEquals(14L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(20L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    public void testNestedParentheses() throws Exception {
        evaluator.evaluateExpression("a = ((2 + 3) * (4 - 1))");

        assertEquals(15L, (long) evaluator.getVariableStore().get("a"));
    }

    @Test
    public void testUninitializedVariable() throws Exception {
        evaluator.evaluateExpression("x += 5");
        assertEquals(5L, (long) evaluator.getVariableStore().get("x"));
    }

    @Test
    public void testVariableStore() {
        VariableStore store = new VariableStore();
        store.set("x", 10);
        store.set("y", 20);

        assertEquals(10L, (long) store.get("x"));
        assertEquals(20L, (long) store.get("y"));
        assertEquals("(x=10,y=20)", store.toString());
    }
}
