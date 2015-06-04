package io.betterlife.erp.domains.order;

import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.framework.domains.security.User;
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
    public void testPurchaseDate() throws Exception {
        Date date = new Date();
        purchaseOrder.setOrderDate(date);
        assertNotNull(purchaseOrder.getOrderDate());
        assertEquals(date, purchaseOrder.getOrderDate());
        Date date1 = new Date();
        purchaseOrder.setOrderDate(date1);
        final Date date2 = purchaseOrder.getOrderDate();
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