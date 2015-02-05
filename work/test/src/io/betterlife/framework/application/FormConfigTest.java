package io.betterlife.framework.application;

import io.betterlife.framework.application.config.FormConfig;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FormConfigTest {

    @Test
    public void testGetInstance() throws Exception {
        FormConfig config = FormConfig.getInstance();
        assertNotNull(config);
    }

    @Test
    public void testGetCreateFormIgnoreFields() throws Exception {
        FormConfig config = FormConfig.getInstance();
        List<String> ignoreFields = config.getCreateFormIgnoreFields();
        assertNotNull(ignoreFields);
        assertEquals(6, ignoreFields.size());
        assertTrue(ignoreFields.contains("id"));
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
        assertTrue(ignoreFields.contains("active"));
    }

    @Test
    public void testGetListFormIgnoreFields() throws Exception {
        FormConfig config = FormConfig.getInstance();
        List<String> ignoreFields = config.getListFormIgnoreFields();
        assertNotNull(ignoreFields);
        assertEquals(6, ignoreFields.size());
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
        assertTrue(ignoreFields.contains("active"));
        assertTrue(ignoreFields.contains("password"));
    }

    @Test
    public void testGetEditFormIgnoreFields() throws Exception {
        FormConfig config = FormConfig.getInstance();
        List<String> ignoreFields = config.getEditFormIgnoreFields();
        assertNotNull(ignoreFields);
        assertEquals(5, ignoreFields.size());
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
        assertTrue(ignoreFields.contains("active"));
    }
}