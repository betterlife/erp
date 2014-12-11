package io.betterlife.domains.financial;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExpenseCategoryTest {

    private ExpenseCategory expenseCategory;

    @Before
    public void setUp() throws Exception {
        expenseCategory = new ExpenseCategory();
    }

    @Test
    public void testConstructorWithParam() {
        final String name = "Expense Category Name";
        ExpenseCategory cc = new ExpenseCategory(name);
        assertNotNull(cc);
        assertEquals(name, cc.getName());
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Expense Category Name";
        expenseCategory.setName(name);
        final String newName = "New Expense Category Name";
        expenseCategory.setName(newName);
        assertNotNull(expenseCategory.getName());
        assertNotEquals(name, expenseCategory.getName());
        assertEquals(newName, expenseCategory.getName());
    }

    @Test
    public void testGetName() throws Exception {
        final String name = "A Expense Category Name";
        expenseCategory.setName(name);
        assertNotNull(expenseCategory.getName());
        assertEquals(name, expenseCategory.getName());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = CostCenter.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(ExpenseCategory.class, ExpenseCategory.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(ExpenseCategory.class, ExpenseCategory.class.getSimpleName() + "." + "getById");
    }
}