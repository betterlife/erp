package io.betterlife.rest;

import io.betterlife.util.TemplateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntityFormServiceTest {

    EntityFormService service;

    @Before
    public void setUp() throws Exception {
        service = new EntityFormService();
    }

    @Test
    public void testGetTemplateUtils() throws Exception {
        assertNotNull(service.getTemplateUtils());
    }

    @Test
    public void testSetTemplateUtils() throws Exception {
        TemplateUtils templateUtils = Mockito.mock(TemplateUtils.class);
        service.setTemplateUtils(templateUtils);
        assertEquals(templateUtils, service.getTemplateUtils());
    }

    @Test
    public void testGetCreateForm() throws Exception {

    }

    @Test
    public void testGetEditForm() throws Exception {

    }

    @Test
    public void testGetForm() throws Exception {

    }

    @Test
    public void testGetListForm() throws Exception {

    }

    @Test
    public void testGetDetailForm() throws Exception {

    }
}