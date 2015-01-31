package io.betterlife.domains.order;

import io.betterlife.domains.catalog.Product;
import io.betterlife.domains.common.Supplier;
import io.betterlife.domains.security.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SalesOrderTest {
    SalesOrder salesOrder;

    @Before
    public void setUp() throws Exception {
        salesOrder = new SalesOrder();
    }

    @Test
    public void testProduct() throws Exception {
        Product product = Mockito.mock(Product.class);
        salesOrder.setProduct(product);
        assertNotNull(salesOrder.getProduct());
        assertSame(product, salesOrder.getProduct());
        Product product1 = Mockito.mock(Product.class);
        salesOrder.setProduct(product1);
        assertNotNull(salesOrder.getProduct());
        assertNotEquals(product, salesOrder.getProduct());
        assertSame(product1, salesOrder.getProduct());
    }

    @Test
    public void testGetSupplier() throws Exception {
        Product product = Mockito.mock(Product.class);
        Supplier supplier = Mockito.mock(Supplier.class);
        when(product.getSupplier()).thenReturn(supplier);
        salesOrder.setProduct(product);
        assertNotNull(salesOrder.getSupplier());
        assertSame(supplier, salesOrder.getSupplier());
        Product newProduct = Mockito.mock(Product.class);
        Supplier newSupplier = Mockito.mock(Supplier.class);
        when(newProduct.getSupplier()).thenReturn(newSupplier);
        salesOrder.setProduct(newProduct);
        assertNotNull(salesOrder.getSupplier());
        assertNotEquals(supplier, salesOrder.getSupplier());
        assertSame(newSupplier, salesOrder.getSupplier());
    }

    @Test
    public void testTransactor() throws Exception {
        User user = Mockito.mock(User.class);
        salesOrder.setTransactor(user);
        assertNotNull(salesOrder.getTransactor());
        assertSame(user, salesOrder.getTransactor());
        User user1 = Mockito.mock(User.class);
        salesOrder.setTransactor(user1);
        assertNotNull(salesOrder.getTransactor());
        assertNotEquals(user, salesOrder.getTransactor());
        assertSame(user1, salesOrder.getTransactor());
    }

    @Test
    public void testQuantity() {
        BigDecimal qty = new BigDecimal("20");
        salesOrder.setQuantity(qty);
        assertNotNull(salesOrder.getQuantity());
        assertSame(qty, salesOrder.getQuantity());
        BigDecimal newQty = new BigDecimal("25.02");
        salesOrder.setQuantity(newQty);
        assertNotNull(salesOrder.getQuantity());
        assertNotEquals(qty, salesOrder.getQuantity());
        assertSame(newQty, salesOrder.getQuantity());
    }

    @Test
    public void testPricePerUnit() {
        BigDecimal ppu = new BigDecimal("20.00");
        salesOrder.setPricePerUnit(ppu);
        assertNotNull(salesOrder.getPricePerUnit());
        assertSame(ppu, salesOrder.getPricePerUnit());
        BigDecimal newPPU = new BigDecimal("100.21");
        salesOrder.setPricePerUnit(newPPU);
        assertNotNull(salesOrder.getPricePerUnit());
        assertNotEquals(ppu, salesOrder.getPricePerUnit());
        assertSame(newPPU, salesOrder.getPricePerUnit());
    }

    @Test
    public void testLogisticAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        salesOrder.setLogisticAmount(bd);
        assertNotNull(salesOrder.getLogisticAmount());
        assertEquals(bd, salesOrder.getLogisticAmount());
        BigDecimal bd1 = new BigDecimal("20.90");
        salesOrder.setLogisticAmount(bd1);
        final BigDecimal amount = salesOrder.getLogisticAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testTotalAmount() throws Exception {
        BigDecimal qty = new BigDecimal("12");
        BigDecimal ppu = new BigDecimal("20.02");
        salesOrder.setQuantity(qty);
        salesOrder.setPricePerUnit(ppu);
        BigDecimal totalAmt = salesOrder.getAmount();
        assertNotNull(totalAmt);
        assertEquals(qty.multiply(ppu), totalAmt);

        BigDecimal ppu1 = new BigDecimal("20.90");
        salesOrder.setPricePerUnit(ppu1);
        BigDecimal totalAmt1 = salesOrder.getAmount();
        assertNotNull(totalAmt1);
        assertNotEquals(qty.multiply(ppu), totalAmt1);
        assertEquals(qty.multiply(ppu1), totalAmt1);

        BigDecimal qty1 = new BigDecimal("24");
        salesOrder.setQuantity(qty1);
        BigDecimal totalAmt2 = salesOrder.getAmount();
        assertNotNull(totalAmt2);
        assertNotEquals(qty.multiply(ppu), totalAmt2);
        assertNotEquals(qty.multiply(ppu1), totalAmt2);
        assertEquals(qty1.multiply(ppu1), totalAmt2);
    }

    @Test
    public void testGetUnitLogisticAmount() throws Exception {
        BigDecimal totalLogisticAmt = new BigDecimal("8");
        salesOrder.setLogisticAmount(totalLogisticAmt);
        BigDecimal qty = new BigDecimal("10");
        salesOrder.setQuantity(qty);
        BigDecimal unitLogisticAmt = salesOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        BigDecimal newTotalLogisticAmt = new BigDecimal("10");
        salesOrder.setLogisticAmount(newTotalLogisticAmt);
        unitLogisticAmt = salesOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertNotEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertEquals(newTotalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        BigDecimal newQty = new BigDecimal("5");
        salesOrder.setQuantity(newQty);
        unitLogisticAmt = salesOrder.getUnitLogisticAmount();
        assertNotNull(unitLogisticAmt);
        assertNotEquals(totalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertNotEquals(newTotalLogisticAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
        assertEquals(newTotalLogisticAmt.divide(newQty, 2, BigDecimal.ROUND_HALF_UP), unitLogisticAmt);
    }

    @Test
    public void testOrderDate() throws Exception {
        Date date = new Date();
        salesOrder.setOrderDate(date);
        assertNotNull(salesOrder.getOrderDate());
        assertEquals(date, salesOrder.getOrderDate());
        Date date1 = new Date();
        salesOrder.setOrderDate(date1);
        final Date date2 = salesOrder.getOrderDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testStockOutDate() throws Exception {
        Date date = new Date();
        salesOrder.setStockOutDate(date);
        assertNotNull(salesOrder.getStockOutDate());
        assertEquals(date, salesOrder.getStockOutDate());
        Date date1 = new Date();
        salesOrder.setStockOutDate(date1);
        final Date date2 = salesOrder.getStockOutDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testRemark() throws Exception {
        String str = "I am a remark with 中文";
        salesOrder.setRemark(str);
        assertNotNull(salesOrder.getRemark());
        assertEquals(str, salesOrder.getRemark());
        String str1 = "I am a 新的 remark with 中文";
        salesOrder.setRemark(str1);
        final String remark = salesOrder.getRemark();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }
}