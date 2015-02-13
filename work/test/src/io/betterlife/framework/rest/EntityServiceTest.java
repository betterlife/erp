package io.betterlife.framework.rest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntityServiceTest {

    EntityService service;

    @Before
    public void setup() {
        service = new EntityService();
    }

    @Test
    public void testGetEntityMeta() throws Exception {

    }

    @Test
    public void testGetObjectByTypeAndId() throws Exception {

    }

    @Test
    public void testGetAllByObjectType() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testGetNamedQueryRule() throws Exception {
        assertNotNull(service.getNamedQueryRule());
    }

    @Test
    public void testGetOperator() throws Exception {
        assertNotNull(service.getBaseOperator());
    }
}