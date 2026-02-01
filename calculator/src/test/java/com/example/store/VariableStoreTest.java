package com.example.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableStoreTest {
    private VariableStore store;

    @BeforeEach 
    void setUp() {
        store = new VariableStore();
    }

    @Test
    void testVariableStore() {
        store.set("x", 10);
        store.set("y", 20);

        assertEquals(10L, (long) store.get("x"));
        assertEquals(20L, (long) store.get("y"));
        assertEquals("(x=10,y=20)", store.toString());
    }

    @Test
    void testSubtraction() throws Exception {
        store.set("a", 10);
        long result = store.get("a") - 3;
        store.set("b", result);

        assertEquals(7L, (long) store.get("b"));
    }

    @Test
    void testMinusAssign() throws Exception {
        store.set("x", 20);
        long newVal = store.get("x") - 5;
        store.set("x", newVal);

        assertEquals(15L, (long) store.get("x"));
    }

    @Test
    void testMultiplyAssign() throws Exception {
        store.set("x", 4);
        long newVal = store.get("x") * 3;
        store.set("x", newVal);

        assertEquals(12L, (long) store.get("x"));
    }

    @Test
    void testDivideAssign() throws Exception {
        store.set("x", 20);
        long newVal = store.get("x") / 4;
        store.set("x", newVal);

        assertEquals(5L, (long) store.get("x"));
    }

    @Test
    void testUninitializedVariable() throws Exception {
        Long val = store.get("x");
        if (val == null) {
            val = 0L;
        }
        store.set("x", val + 5);
        assertEquals(5L, (long) store.get("x"));
    }
}
