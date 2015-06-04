package io.betterlife.framework.rest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityDataServiceTest {

    EntityDataService service;

    @Before
    public void setup() {
        service = new EntityDataService();
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