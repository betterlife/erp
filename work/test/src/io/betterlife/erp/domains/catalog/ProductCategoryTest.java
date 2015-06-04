package io.betterlife.erp.domains.catalog;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.Entity;

import static org.junit.Assert.*;

public class ProductCategoryTest {

    ProductCategory productCategory;
    @Before
    public void setUp() throws Exception {
        productCategory = new ProductCategory();
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Expense Category Name";
        productCategory.setName(name);
        final String newName = "New Expense Category Name";
        productCategory.setName(newName);
        assertNotNull(productCategory.getName());
        assertNotEquals(name, productCategory.getName());
        assertEquals(newName, productCategory.getName());
    }

    @Test
    public void testGetName() throws Exception {
        final String name = "A Expense Category Name";
        productCategory.setName(name);
        assertNotNull(productCategory.getName());
        assertEquals(name, productCategory.getName());
    }

    @Test
    public void testCode() throws Exception {
        final String code = "11001100";
        productCategory.setCode(code);
        assertNotNull(productCategory.getCode());
        assertEquals(code, productCategory.getCode());
        final String newCode = "11001101";
        productCategory.setCode(newCode);
        assertNotNull(productCategory.getCode());
        assertNotEquals(code, productCategory.getCode());
        assertEquals(newCode, productCategory.getCode());
    }


    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = ProductCategory.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(ProductCategory.class, ProductCategory.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(ProductCategory.class, ProductCategory.class.getSimpleName() + "." + "getById");
    }
}