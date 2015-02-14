package io.betterlife.framework.condition;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PasswordVisibleConditionTest {

    @Test
    public void testEvaluateTrue() throws Exception {
        assertTrue(new PasswordVisibleCondition().evaluate("user", null, null, "Create"));
        assertTrue(new PasswordVisibleCondition().evaluate("User", null, null, "Create"));
        assertTrue(new PasswordVisibleCondition().evaluate("user", null, null, "Update"));
        assertTrue(new PasswordVisibleCondition().evaluate("User", null, null, "Update"));
        assertTrue(new PasswordVisibleCondition().evaluate("Expense", null, null, "Create"));
    }

    @Test
    public void testEvaluateListFalse() throws Exception {
        assertFalse(new PasswordVisibleCondition().evaluate("User", null, null, "List"));
        assertFalse(new PasswordVisibleCondition().evaluate("user", null, null, "List"));
        assertFalse(new PasswordVisibleCondition().evaluate("User", null, null, "Detail"));
    }

}