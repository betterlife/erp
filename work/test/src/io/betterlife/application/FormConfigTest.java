package io.betterlife.application;

import io.betterlife.application.config.FormConfig;
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
        assertEquals(5, ignoreFields.size());
        assertTrue(ignoreFields.contains("id"));
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
    }

    @Test
    public void testGetListFormIgnoreFields() throws Exception {
        FormConfig config = FormConfig.getInstance();
        List<String> ignoreFields = config.getListFormIgnoreFields();
        assertNotNull(ignoreFields);
        assertEquals(4, ignoreFields.size());
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
    }

    @Test
    public void testGetEditFormIgnoreFields() throws Exception {
        FormConfig config = FormConfig.getInstance();
        List<String> ignoreFields = config.getEditFormIgnoreFields();
        assertNotNull(ignoreFields);
        assertEquals(4, ignoreFields.size());
        assertTrue(ignoreFields.contains("lastModifyDate"));
        assertTrue(ignoreFields.contains("lastModify"));
        assertTrue(ignoreFields.contains("createDate"));
        assertTrue(ignoreFields.contains("creator"));
    }
}