package io.betterlife.persistence;

import org.junit.Test;

import static org.junit.Assert.*;

public class NamedQueryRulesTest {

    NamedQueryRules namedQueryRules = NamedQueryRules.getInstance();

    @Test
    public void testGetIdQueryForEntityLowerCase() throws Exception {
        String query = namedQueryRules.getIdQueryForEntity("user");
        assertEquals("User.getById", query);
    }

    @Test
    public void testGetIdQueryForEntityUpperCase() throws Exception {
        String query = namedQueryRules.getIdQueryForEntity("Expense");
        assertEquals("Expense.getById", query);
    }

    @Test
    public void testGetAllQueryForEntityLowerCase() throws Exception {
        String query = namedQueryRules.getAllQueryForEntity("costCenter");
        assertEquals("CostCenter.getAll", query);
    }

    @Test
    public void testGetAllQueryForEntityUpperCase() throws Exception {
        String query = namedQueryRules.getAllQueryForEntity("Permission");
        assertEquals("Permission.getAll", query);
    }

}