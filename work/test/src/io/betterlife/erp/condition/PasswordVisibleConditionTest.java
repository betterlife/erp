package io.betterlife.erp.condition;

import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.condition.Condition;
import io.betterlife.framework.condition.PasswordVisibleCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PasswordVisibleConditionTest {
    Condition condition;
    FieldMeta meta;

    @Before
    public void setup(){
        condition = new PasswordVisibleCondition();
        meta = Mockito.mock(FieldMeta.class);
        when(meta.getName()).thenReturn("password");
    }

    @Test
    public void shouldVisibleWhenCreating() throws Exception {
        assertTrue(condition.evaluate("user", meta, null, Operation.CREATE));
    }

    @Test
    public void shouldVisibleWhenUpdating() throws Exception {
        assertTrue(condition.evaluate("user", meta, null, Operation.UPDATE));
    }

    @Test
    public void shouldVisibleWhenListing() throws Exception {
        assertFalse(condition.evaluate("user", meta, null, Operation.LIST));
    }

    @Test
    public void shouldVisibleWhenObjectIsNotUser() {
        assertTrue(condition.evaluate("product", meta, null, Operation.CREATE));
        assertTrue(condition.evaluate("product", meta, null, Operation.UPDATE));
        assertTrue(condition.evaluate("product", meta, null, Operation.LIST));
    }
}