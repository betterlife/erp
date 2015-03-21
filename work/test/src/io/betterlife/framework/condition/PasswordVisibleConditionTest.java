package io.betterlife.framework.condition;

import io.betterlife.framework.constant.Operation;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PasswordVisibleConditionTest {

    @Test
    public void testEvaluateTrue() throws Exception {
        assertTrue(new PasswordVisibleCondition().evaluate("user", null, null, Operation.CREATE));
        assertTrue(new PasswordVisibleCondition().evaluate("User", null, null, Operation.CREATE));
        assertTrue(new PasswordVisibleCondition().evaluate("user", null, null, Operation.UPDATE));
        assertTrue(new PasswordVisibleCondition().evaluate("User", null, null, Operation.UPDATE));
        assertTrue(new PasswordVisibleCondition().evaluate("Expense", null, null, Operation.CREATE));
    }

    @Test
    public void testEvaluateListFalse() throws Exception {
        assertFalse(new PasswordVisibleCondition().evaluate("User", null, null, Operation.LIST));
        assertFalse(new PasswordVisibleCondition().evaluate("user", null, null, Operation.LIST));
        assertFalse(new PasswordVisibleCondition().evaluate("User", null, null, Operation.DETAIL));
    }

}