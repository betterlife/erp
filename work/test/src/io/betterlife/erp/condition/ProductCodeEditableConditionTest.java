package io.betterlife.erp.condition;

import io.betterlife.framework.constant.Operation;
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
        assertTrue(condition.evaluate("product", meta, null, Operation.CREATE));
    }

    @Test
    public void shouldNonEditableWhenUpdating() {
        assertFalse(condition.evaluate("product", meta, null, Operation.UPDATE));
    }

    @Test
    public void shouldEditableWhenNotProduct(){
        assertTrue(condition.evaluate("User", meta, null, Operation.CREATE));
        assertTrue(condition.evaluate("User", meta, null, Operation.UPDATE));
        assertTrue(condition.evaluate("User", meta, null, Operation.LIST));
    }
}