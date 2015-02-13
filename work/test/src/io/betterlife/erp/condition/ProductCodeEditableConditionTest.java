package io.betterlife.erp.condition;

import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.condition.Condition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductCodeEditableConditionTest {
    Condition condition;
    FieldMeta meta;

    @Before
    public void setup(){
        condition = new ProductCodeEditableCondition();
        meta = Mockito.mock(FieldMeta.class);
        when(meta.getName()).thenReturn("code");
    }

    @Test
    public void shouldEditableWhenCreate() throws Exception {
        assertTrue(condition.evaluate("product", meta, null, "Create"));
    }

    @Test
    public void shouldNonEditableWhenUpdating() {
        assertFalse(condition.evaluate("product", meta, null, "Update"));
    }

    @Test
    public void shouldEditableWhenNotProduct(){
        assertTrue(condition.evaluate("User", meta, null, "Create"));
        assertTrue(condition.evaluate("User", meta, null, "Update"));
        assertTrue(condition.evaluate("User", meta, null, "List"));
    }
}