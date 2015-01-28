package io.betterlife.domains.catalog;

import io.betterlife.domains.common.Supplier;
import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.Entity;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductTest {

    Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product();
    }

    @Test
    public void testGetCode() throws Exception {
        final String code = "110011222000";
        product.setCode(code);
        assertNotNull(product.getCode());
        assertEquals(code, product.getCode());
    }

    @Test
    public void testSetCode() throws Exception {
        final String code = "110011222000";
        product.setCode(code);
        final String newCode = "110011222001";
        product.setCode(newCode);
        assertNotNull(product.getCode());
        assertNotEquals(code, product.getCode());
        assertSame(newCode, product.getCode());
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Product Name";
        product.setName(name);
        final String newName = "New Product Name";
        product.setName(newName);
        assertNotNull(product.getName());
        assertNotEquals(name, product.getName());
        assertSame(newName, product.getName());
    }

    @Test
    public void testGetName() throws Exception {
        final String name = "A Product Name";
        product.setName(name);
        assertNotNull(product.getName());
        assertSame(name, product.getName());
    }

    @Test
    public void testGetCategory() throws Exception {
        ProductCategory parent = Mockito.mock(ProductCategory.class);
        product.setCategory(parent);
        assertNotNull(product.getCategory());
        assertSame(parent, product.getCategory());
    }

    @Test
    public void testSetCategory() throws Exception {
        ProductCategory category = Mockito.mock(ProductCategory.class);
        product.setCategory(category);
        ProductCategory newCate = Mockito.mock(ProductCategory.class);
        product.setCategory(newCate);
        assertNotNull(product.getCategory());
        assertNotEquals(category, product.getCategory());
        assertSame(newCate, product.getCategory());
    }

    @Test
    public void testGetSupplier() throws Exception {
        Supplier supplier = Mockito.mock(Supplier.class);
        product.setSupplier(supplier);
        assertNotNull(product.getSupplier());
        assertSame(supplier, product.getSupplier());
    }

    @Test
    public void testSetSupplier() throws Exception {
        Supplier supplier = Mockito.mock(Supplier.class);
        product.setSupplier(supplier);
        Supplier newSupplier = Mockito.mock(Supplier.class);
        product.setSupplier(newSupplier);
        assertNotNull(product.getSupplier());
        assertNotEquals(supplier, product.getSupplier());
        assertSame(newSupplier, product.getSupplier());
    }

    @Test
    public void testGetPurchasePrice() throws Exception {
        BigDecimal bd = new BigDecimal("0.01");
        product.setPurchasePrice(bd);
        final BigDecimal amount = product.getPurchasePrice();
        assertNotNull(amount);
        assertSame(bd, amount);
    }

    @Test
    public void testSetPurchasePrice() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        product.setPurchasePrice(bd);
        BigDecimal bd1 = new BigDecimal("20.90");
        product.setPurchasePrice(bd1);
        final BigDecimal amount = product.getPurchasePrice();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetRetailPrice() throws Exception {
        BigDecimal bd = new BigDecimal("0.01");
        product.setRetailPrice(bd);
        final BigDecimal amount = product.getRetailPrice();
        assertNotNull(amount);
        assertSame(bd, amount);
    }

    @Test
    public void testSetRetailPrice() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        product.setRetailPrice(bd);
        BigDecimal bd1 = new BigDecimal("20.90");
        product.setRetailPrice(bd1);
        final BigDecimal amount = product.getRetailPrice();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetLink() throws Exception {
        String str = "I am a link with 中文";
        product.setLink(str);
        final String link = product.getLink();
        assertNotNull(link);
        assertSame(str, link);
    }

    @Test
    public void testSetLink() throws Exception {
        String str = "I am a remark with 中文";
        product.setLink(str);
        String str1 = "I am a 新的 remark with 中文";
        product.setLink(str1);
        final String remark = product.getLink();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }

    @Test
    public void testGetDistinguishingFeature() throws Exception {
        String str = "I am a df with 中文";
        product.setDistinguishingFeature(str);
        final String df = product.getDistinguishingFeature();
        assertNotNull(df);
        assertSame(str, df);
    }

    @Test
    public void testSetDistinguishingFeature() throws Exception {
        String str = "I am a df with 中文";
        product.setDistinguishingFeature(str);
        String str1 = "I am a 新的 df with 中文";
        product.setDistinguishingFeature(str1);
        final String df = product.getDistinguishingFeature();
        assertNotNull(df);
        assertNotEquals(str, df);
        assertSame(str1, df);
    }

    @Test
    public void testGetLeadDay() throws Exception {
        int ld = 1;
        product.setLeadDay(ld);
        assertNotNull(product.getLeadDay());
        assertEquals(ld, product.getLeadDay());
        int nld = 2;
        product.setLeadDay(nld);
        assertNotNull(product.getLeadDay());
        assertNotEquals(ld, product.getLeadDay());
        assertEquals(nld, product.getLeadDay());
    }

    @Test
    public void testDeliverDay() throws Exception {
        int ld = 1;
        product.setDeliverDay(ld);
        assertNotNull(product.getDeliverDay());
        assertEquals(ld, product.getDeliverDay());
        int nld = 2;
        product.setDeliverDay(nld);
        assertNotNull(product.getDeliverDay());
        assertNotEquals(ld, product.getDeliverDay());
        assertEquals(nld, product.getDeliverDay());
    }

    @Test
    public void testGetGrossProfitRateString() {
        BigDecimal pp = new BigDecimal("120");
        product.setPurchasePrice(pp);
        BigDecimal rp = new BigDecimal("240");
        product.setRetailPrice(rp);
        assertNotNull(product.getGrossProfitRateString());
        assertEquals("50.00%", product.getGrossProfitRateString());
        BigDecimal nPP = new BigDecimal("60");
        product.setPurchasePrice(nPP);
        assertNotNull(product.getGrossProfitRateString());
        assertEquals("75.00%", product.getGrossProfitRateString());
        BigDecimal nRP = new BigDecimal("90");
        product.setRetailPrice(nRP);
        assertNotNull(product.getGrossProfitRateString());
        assertEquals("33.33%", product.getGrossProfitRateString());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = Product.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Product.class, Product.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Product.class, Product.class.getSimpleName() + "." + "getById");
    }

}