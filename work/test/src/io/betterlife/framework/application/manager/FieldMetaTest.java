package io.betterlife.framework.application.manager;

import io.betterlife.framework.meta.FieldMeta;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class FieldMetaTest {

    FieldMeta meta;
    @Before
    public void setUp() throws Exception {
        meta = new FieldMeta();
    }

    @Test
    public void testField() {
        meta.setName("fieldName");
        assertEquals("fieldName", meta.getName());
        meta.setName("newFieldName");
        assertNotEquals("fieldName", meta.getName());
        assertEquals("newFieldName", meta.getName());
    }

    @Test
    public void testType(){
        meta.setType(String.class);
        assertEquals(String.class, meta.getType());
        meta.setType(Date.class);
        assertNotEquals(String.class, meta.getType());
        assertEquals(Date.class, meta.getType());
    }

}