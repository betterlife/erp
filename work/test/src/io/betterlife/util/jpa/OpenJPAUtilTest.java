package io.betterlife.util.jpa;

import org.apache.openjpa.persistence.OpenJPAQuery;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OpenJPAUtilTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(OpenJPAUtil.getInstance());
    }

    @Test
    public void testGetOpenJPAQuery() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        OpenJPAQuery query = mock(OpenJPAQuery.class);
        final String queryName = "User.getAll";
        when(entityManager.createNamedQuery(queryName)).thenReturn(query);
        final OpenJPAUtil openJPAUtil = OpenJPAUtil.getInstance();
        openJPAUtil.setEntityManager(entityManager);
        assertEquals(query, openJPAUtil.getOpenJPAQuery(queryName));
    }

    @Test
    public void testGetSingleResult() throws Exception {
        internalGetSingleResultTest("xxxxx");
    }

    @Test
    public void testGetSingleResultInt() throws Exception {
        internalGetSingleResultTest(10);
    }

    private <T> void internalGetSingleResultTest(T expect) {
        OpenJPAQuery query = mock(OpenJPAQuery.class);
        when(query.getSingleResult()).thenReturn(expect);
        T result = OpenJPAUtil.getInstance().getSingleResult(query);
        assertEquals(expect, result);
    }
}