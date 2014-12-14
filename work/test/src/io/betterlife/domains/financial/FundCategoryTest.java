package io.betterlife.domains.financial;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

import static org.junit.Assert.*;

public class FundCategoryTest {

    private FundCategory fundCategory;

    @Before
    public void setUp() throws Exception {
        fundCategory = new FundCategory();
    }

    @Test
    public void testConstructorWithParam() {
        final String name = "Expense Category Name";
        FundCategory cc = new FundCategory(name);
        assertNotNull(cc);
        assertEquals(name, cc.getName());
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Expense Category Name";
        fundCategory.setName(name);
        final String newName = "New Expense Category Name";
        fundCategory.setName(newName);
        assertNotNull(fundCategory.getName());
        assertNotEquals(name, fundCategory.getName());
        assertEquals(newName, fundCategory.getName());
    }

    @Test
    public void testGetName() throws Exception {
        final String name = "A Expense Category Name";
        fundCategory.setName(name);
        assertNotNull(fundCategory.getName());
        assertEquals(name, fundCategory.getName());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = CostCenter.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(FundCategory.class, FundCategory.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(FundCategory.class, FundCategory.class.getSimpleName() + "." + "getById");
    }
}