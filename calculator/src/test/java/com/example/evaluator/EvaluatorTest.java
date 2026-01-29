package com.example.evaluator;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Evaluator class - Evaluation layer
 * Tests individual operations and expressions
 */
public class EvaluatorTest {
    private Evaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new Evaluator();
    }

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
}
