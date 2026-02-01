package com.example.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EvaluatorTest {

    @Autowired
    Evaluator evaluator;

    @Test
    void testSimpleAssignment() throws Exception {
        evaluator.evaluateExpression("i = 0");
        assertEquals(0L, (long) evaluator.getVariableStore().get("i"));
    }

    @Test
    void testPrefixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 0");
        evaluator.evaluateExpression("j = ++i");

        assertEquals(1L, (long) evaluator.getVariableStore().get("i"));
        assertEquals(1L, (long) evaluator.getVariableStore().get("j"));
    }

    @Test
    void testPostfixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 5");
        evaluator.evaluateExpression("j = i++");

        assertEquals(6L, (long) evaluator.getVariableStore().get("i"));
        assertEquals(5L, (long) evaluator.getVariableStore().get("j"));
    }

    @Test
    void testAddition() throws Exception {
        evaluator.evaluateExpression("a = 10");
        evaluator.evaluateExpression("b = a + 5");

        assertEquals(10L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(15L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    void testOperatorPrecedence() throws Exception {
        evaluator.evaluateExpression("y = (5 + 3) * 10");
        assertEquals(80L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    void testAdditionAssignment() throws Exception {
        evaluator.evaluateExpression("i = 10");
        evaluator.evaluateExpression("i += 5");

        assertEquals(15L, (long) evaluator.getVariableStore().get("i"));
    }

    @Test
    void testMultiplication() throws Exception {
        evaluator.evaluateExpression("a = 3");
        evaluator.evaluateExpression("b = a * 4");

        assertEquals(12L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    void testDivision() throws Exception {
        evaluator.evaluateExpression("a = 20");
        evaluator.evaluateExpression("b = a / 4");

        assertEquals(5L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    void testMultipleOperations() throws Exception {
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
    void testComplexExpression() throws Exception {
        evaluator.evaluateExpression("a = 2");
        evaluator.evaluateExpression("b = 3");
        evaluator.evaluateExpression("c = a + b * 2");

        assertEquals(2L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(3L, (long) evaluator.getVariableStore().get("b"));
        assertEquals(8L, (long) evaluator.getVariableStore().get("c"));
    }

    @Test
    void testPrefixDecrement() throws Exception {
        evaluator.evaluateExpression("x = 10");
        evaluator.evaluateExpression("y = --x");

        assertEquals(9L, (long) evaluator.getVariableStore().get("x"));
        assertEquals(9L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    void testPostfixDecrement() throws Exception {
        evaluator.evaluateExpression("x = 10");
        evaluator.evaluateExpression("y = x--");

        assertEquals(9L, (long) evaluator.getVariableStore().get("x"));
        assertEquals(10L, (long) evaluator.getVariableStore().get("y"));
    }

    @Test
    void testParenthesesPrecedence() throws Exception {
        evaluator.evaluateExpression("a = 2 + 3 * 4");
        evaluator.evaluateExpression("b = (2 + 3) * 4");

        assertEquals(14L, (long) evaluator.getVariableStore().get("a"));
        assertEquals(20L, (long) evaluator.getVariableStore().get("b"));
    }

    @Test
    void testNestedParentheses() throws Exception {
        evaluator.evaluateExpression("a = ((2 + 3) * (4 - 1))");

        assertEquals(15L, (long) evaluator.getVariableStore().get("a"));
    }
}
