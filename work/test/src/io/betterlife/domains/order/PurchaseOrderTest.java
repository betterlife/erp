package io.betterlife.domains.order;

import io.betterlife.domains.catalog.Product;
import io.betterlife.domains.common.Supplier;
import io.betterlife.domains.security.User;
import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PurchaseOrderTest {

    PurchaseOrder purchaseOrder;

    @Before
    public void setUp() throws Exception {
        purchaseOrder = new PurchaseOrder();
    }

    @Test
    public void testProduct() throws Exception {
        Product product = Mockito.mock(Product.class);
        purchaseOrder.setProduct(product);
        assertNotNull(purchaseOrder.getProduct());
        assertSame(product, purchaseOrder.getProduct());
        Product product1 = Mockito.mock(Product.class);
        purchaseOrder.setProduct(product1);
        assertNotNull(purchaseOrder.getProduct());
        assertNotEquals(product, purchaseOrder.getProduct());
        assertSame(product1, purchaseOrder.getProduct());
    }

    @Test
    public void testGetSupplier() throws Exception {
        Product product = Mockito.mock(Product.class);
        Supplier supplier = Mockito.mock(Supplier.class);
        when(product.getSupplier()).thenReturn(supplier);
        purchaseOrder.setProduct(product);
        assertNotNull(purchaseOrder.getSupplier());
        assertSame(supplier, purchaseOrder.getSupplier());
        Product newProduct = Mockito.mock(Product.class);
        Supplier newSupplier = Mockito.mock(Supplier.class);
        when(newProduct.getSupplier()).thenReturn(newSupplier);
        purchaseOrder.setProduct(newProduct);
        assertNotNull(purchaseOrder.getSupplier());
        assertNotEquals(supplier, purchaseOrder.getSupplier());
        assertSame(newSupplier, purchaseOrder.getSupplier());
    }

    @Test
    public void testPricePerUnit() throws Exception {
        BigDecimal ppu = new BigDecimal("20.00");
        purchaseOrder.setPricePerUnit(ppu);
        assertNotNull(purchaseOrder.getPricePerUnit());
        assertSame(ppu, purchaseOrder.getPricePerUnit());
        BigDecimal newPPU = new BigDecimal("100.21");
        purchaseOrder.setPricePerUnit(newPPU);
        assertNotNull(purchaseOrder.getPricePerUnit());
        assertNotEquals(ppu, purchaseOrder.getPricePerUnit());
        assertSame(newPPU, purchaseOrder.getPricePerUnit());
    }

    @Test
    public void testQuantity() throws Exception {
        BigDecimal qty = new BigDecimal("20.00");
        purchaseOrder.setQuantity(qty);
        assertNotNull(purchaseOrder.getQuantity());
        assertSame(qty, purchaseOrder.getQuantity());
        BigDecimal newQty = new BigDecimal("100.00");
        purchaseOrder.setQuantity(newQty);
        assertNotNull(purchaseOrder.getQuantity());
        assertNotEquals(qty, purchaseOrder.getQuantity());
        assertSame(newQty, purchaseOrder.getQuantity());
    }

    @Test
    public void testAmount() throws Exception {
        BigDecimal qty = new BigDecimal("20.00");
        purchaseOrder.setQuantity(qty);
        BigDecimal ppu = new BigDecimal("50.00");
        purchaseOrder.setPricePerUnit(ppu);
        BigDecimal amount = purchaseOrder.getAmount();
        assertNotNull(amount);
        assertEquals(qty.multiply(ppu), amount);
        BigDecimal newQty = new BigDecimal("21");
        purchaseOrder.setQuantity(newQty);
        BigDecimal newAmt = purchaseOrder.getAmount();
        assertNotNull(newAmt);
        assertNotEquals(qty.multiply(ppu), newAmt);
        assertEquals(newQty.multiply(ppu), newAmt);
        BigDecimal newPpu = new BigDecimal("45.00");
        purchaseOrder.setPricePerUnit(newPpu);
        BigDecimal newAmt2 = purchaseOrder.getAmount();
        assertNotNull(newAmt2);
        assertNotEquals(qty.multiply(ppu), newAmt2);
        assertNotEquals(newQty.multiply(ppu), newAmt2);
        assertEquals(newQty.multiply(newPpu), newAmt2);
    }

    @Test
    public void testPurchaseDate() throws Exception {
        Date date = new Date();
        purchaseOrder.setPurchaseDate(date);
        assertNotNull(purchaseOrder.getPurchaseDate());
        assertEquals(date, purchaseOrder.getPurchaseDate());
        Date date1 = new Date();
        purchaseOrder.setPurchaseDate(date1);
        final Date date2 = purchaseOrder.getPurchaseDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testStockInDate() throws Exception {
        Date date = new Date();
        purchaseOrder.setStockInDate(date);
        assertNotNull(purchaseOrder.getStockInDate());
        assertSame(date, purchaseOrder.getStockInDate());
        Date date1 = new Date();
        purchaseOrder.setStockInDate(date1);
        final Date date2 = purchaseOrder.getStockInDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testGetLogisticAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        purchaseOrder.setLogisticAmount(bd);
        assertNotNull(purchaseOrder.getLogisticAmount());
        assertEquals(bd, purchaseOrder.getLogisticAmount());
        BigDecimal bd1 = new BigDecimal("20.90");
        purchaseOrder.setLogisticAmount(bd1);
        final BigDecimal amount = purchaseOrder.getLogisticAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetUnitLogisticAmount() throws Exception {
        BigDecimal totalLogisticAmt = new BigDecimal("8");
        purchaseOrder.setLogisticAmount(totalLogisticAmt);
        BigDecimal qty = new BigDecimal("10");
        purchaseOrder.setQuantity(qty);
        BigDecimal unitLogisticAmt = purchaseOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        BigDecimal newTotalLogisticAmt = new BigDecimal("10");
        purchaseOrder.setLogisticAmount(newTotalLogisticAmt);
        unitLogisticAmt = purchaseOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertNotEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertEquals(newTotalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        BigDecimal newQty = new BigDecimal("5");
        purchaseOrder.setQuantity(newQty);
        unitLogisticAmt = purchaseOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertNotEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertNotEquals(newTotalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertEquals(newTotalLogisticAmt.divide(newQty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
    }

    @Test
    public void testOtherAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        purchaseOrder.setOtherAmount(bd);
        assertNotNull(purchaseOrder.getOtherAmount());
        assertEquals(bd, purchaseOrder.getOtherAmount());
        BigDecimal bd1 = new BigDecimal("20.90");
        purchaseOrder.setOtherAmount(bd1);
        final BigDecimal amount = purchaseOrder.getOtherAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testUser() throws Exception {
        User user = mock(User.class);
        purchaseOrder.setUser(user);
        User user1 = mock(User.class);
        assertNotNull(purchaseOrder.getUser());
        assertSame(user, purchaseOrder.getUser());
        purchaseOrder.setUser(user1);
        assertNotNull(purchaseOrder.getUser());
        assertNotEquals(user, purchaseOrder.getUser());
        assertSame(user1, purchaseOrder.getUser());
    }

    @Test
    public void testRemark() throws Exception {
        String str = "I am a remark with 中文";
        purchaseOrder.setRemark(str);
        assertNotNull(purchaseOrder.getRemark());
        assertEquals(str, purchaseOrder.getRemark());
        String str1 = "I am a 新的 remark with 中文";
        purchaseOrder.setRemark(str1);
        final String remark = purchaseOrder.getRemark();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = PurchaseOrder.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(PurchaseOrder.class, PurchaseOrder.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(PurchaseOrder.class, PurchaseOrder.class.getSimpleName() + "." + "getById");
    }
}