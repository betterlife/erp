package io.betterlife.util.jpa;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JPAUtilTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(JPAUtil.getInstance());
    }

    @Test
    public void testGetJPAQuery() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        Query query = mock(Query.class);
        final String queryName = "User.getAll";
        when(entityManager.createNamedQuery(queryName)).thenReturn(query);
        final JPAUtil util = JPAUtil.getInstance();
        util.setEntityManager(entityManager);
        assertEquals(query, util.getQuery(queryName));
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
        Query query = mock(Query.class);
        when(query.getSingleResult()).thenReturn(expect);
        T result = JPAUtil.getInstance().getSingleResult(query);
        assertEquals(expect, result);
    }
}