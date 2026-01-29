package com.example.evaluator;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class EvaluatorTest {
    private Evaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new Evaluator();
    }

    @Test
    public void testSimpleAssignment() throws Exception {
        evaluator.evaluateExpression("i = 0");
        assertEquals(0.0, evaluator.getVariableStore().get("i"), 0.001);
    }

    @Test
    public void testPrefixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 0");
        evaluator.evaluateExpression("j = ++i");

        assertEquals(1.0, evaluator.getVariableStore().get("i"), 0.001);
        assertEquals(1.0, evaluator.getVariableStore().get("j"), 0.001);
    }

    @Test
    public void testPostfixIncrement() throws Exception {
        evaluator.evaluateExpression("i = 5");
        evaluator.evaluateExpression("j = i++");

        assertEquals(6.0, evaluator.getVariableStore().get("i"), 0.001);
        assertEquals(5.0, evaluator.getVariableStore().get("j"), 0.001);
    }

    @Test
    public void testArithmetic() throws Exception {
        evaluator.evaluateExpression("a = 10");
        evaluator.evaluateExpression("b = a + 5");

        assertEquals(10.0, evaluator.getVariableStore().get("a"), 0.001);
        assertEquals(15.0, evaluator.getVariableStore().get("b"), 0.001);
    }

    @Test
    public void testOperatorPrecedence() throws Exception {
        evaluator.evaluateExpression("y = (5 + 3) * 10");
        assertEquals(80.0, evaluator.getVariableStore().get("y"), 0.001);
    }

    @Test
    public void testCompoundAssignment() throws Exception {
        evaluator.evaluateExpression("i = 10");
        evaluator.evaluateExpression("i += 5");

        assertEquals(15.0, evaluator.getVariableStore().get("i"), 0.001);
    }

    @Test
    public void testMultiplication() throws Exception {
        evaluator.evaluateExpression("a = 3");
        evaluator.evaluateExpression("b = a * 4");

        assertEquals(12.0, evaluator.getVariableStore().get("b"), 0.001);
    }

    @Test
    public void testDivision() throws Exception {
        evaluator.evaluateExpression("a = 20");
        evaluator.evaluateExpression("b = a / 4");

        assertEquals(5.0, evaluator.getVariableStore().get("b"), 0.001);
    }

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
}

