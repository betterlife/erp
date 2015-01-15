package io.betterlife.domains.catalog;

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
    public void testGetParentCategory() throws Exception {
        ProductCategory parent = Mockito.mock(ProductCategory.class);
        productCategory.setParentCategory(parent);
        assertNotNull(productCategory.getParentCategory());
        assertSame(parent, productCategory.getParentCategory());
    }

    @Test
    public void testSetParentCategory() throws Exception {
        ProductCategory parent = Mockito.mock(ProductCategory.class);
        productCategory.setParentCategory(parent);
        ProductCategory parent1 = Mockito.mock(ProductCategory.class);
        productCategory.setParentCategory(parent1);
        assertNotNull(productCategory.getParentCategory());
        assertNotEquals(parent, productCategory.getParentCategory());
        assertSame(parent1, productCategory.getParentCategory());
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