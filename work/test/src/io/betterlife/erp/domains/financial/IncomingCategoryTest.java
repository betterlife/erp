package io.betterlife.erp.domains.financial;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

import static org.junit.Assert.*;

public class IncomingCategoryTest {

    private IncomingCategory incomingCategory;

    @Before
    public void setUp() throws Exception {
        incomingCategory = new IncomingCategory();
    }

    @Test
    public void testConstructorWithParam() {
        final String name = "Incoming Category Name";
        IncomingCategory cc = new IncomingCategory(name);
        assertNotNull(cc);
        assertEquals(name, cc.getName());
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Incoming Category Name";
        incomingCategory.setName(name);
        final String newName = "New Incoming Category Name";
        incomingCategory.setName(newName);
        assertNotNull(incomingCategory.getName());
        assertNotEquals(name, incomingCategory.getName());
        assertEquals(newName, incomingCategory.getName());
    }

    @Test
    public void testGetName() throws Exception {
        final String name = "A Incoming Category Name";
        incomingCategory.setName(name);
        assertNotNull(incomingCategory.getName());
        assertEquals(name, incomingCategory.getName());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = IncomingCategory.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(IncomingCategory.class, IncomingCategory.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(IncomingCategory.class, IncomingCategory.class.getSimpleName() + "." + "getById");
    }

}