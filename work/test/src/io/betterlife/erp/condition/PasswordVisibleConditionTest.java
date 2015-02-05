package io.betterlife.erp.condition;

import io.betterlife.framework.application.manager.FieldMeta;
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
        assertTrue(condition.evaluate("user", meta, null, "Create"));
    }

    @Test
    public void shouldVisibleWhenUpdating() throws Exception {
        assertTrue(condition.evaluate("user", meta, null, "Update"));
    }

    @Test
    public void shouldVisibleWhenListing() throws Exception {
        assertFalse(condition.evaluate("user", meta, null, "List"));
    }

    @Test
    public void shouldVisibleWhenObjectIsNotUser() {
        assertTrue(condition.evaluate("product", meta, null, "Create"));
        assertTrue(condition.evaluate("product", meta, null, "Update"));
        assertTrue(condition.evaluate("product", meta, null, "List"));
    }
}