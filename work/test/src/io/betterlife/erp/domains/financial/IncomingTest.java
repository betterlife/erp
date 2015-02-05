package io.betterlife.erp.domains.financial;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class IncomingTest {

    private Incoming incoming;

    @Before
    public void setUp() {
        incoming = new Incoming();
    }

    @Test
    public void testSetIncomingCategory() throws Exception {
        IncomingCategory category = mock(IncomingCategory.class);
        incoming.setIncomingCategory(category);
        IncomingCategory category1 = mock(IncomingCategory.class);
        incoming.setIncomingCategory(category1);
        final IncomingCategory incomingCategory = incoming.getIncomingCategory();
        assertNotNull(incomingCategory);
        assertNotEquals(category, incomingCategory);
        assertSame(category1, incomingCategory);
    }

    @Test
    public void testGetIncomingCategory() throws Exception {
        IncomingCategory category = mock(IncomingCategory.class);
        incoming.setIncomingCategory(category);
        final IncomingCategory incomingCategory = incoming.getIncomingCategory();
        assertNotNull(incomingCategory);
        assertSame(category, incomingCategory);
    }

    @Test
    public void testSetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        incoming.setAmount(bd);
        BigDecimal bd1 = new BigDecimal("20.90");
        incoming.setAmount(bd1);
        final BigDecimal amount = incoming.getAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("0.01");
        incoming.setAmount(bd);
        final BigDecimal amount = incoming.getAmount();
        assertNotNull(amount);
        assertSame(bd, amount);
    }

    @Test
    public void testSetRemark() throws Exception {
        String str = "I am a remark with 中文";
        incoming.setRemark(str);
        String str1 = "I am a 新的 remark with 中文";
        incoming.setRemark(str1);
        final String remark = incoming.getRemark();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }

    @Test
    public void testGetRemark() throws Exception {
        String str = "I am a remark with 中文";
        incoming.setRemark(str);
        final String remark = incoming.getRemark();
        assertNotNull(remark);
        assertSame(str, remark);
    }

    @Test
    public void testSetDate() throws Exception {
        Date date = new Date();
        incoming.setDate(date);
        Date date1 = new Date();
        incoming.setDate(date1);
        final Date date2 = incoming.getDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testGetDate() throws Exception {
        Date date = new Date();
        incoming.setDate(date);
        final Date date1 = incoming.getDate();
        assertNotNull(date1);
        assertSame(date, date1);
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = Incoming.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Incoming.class, Incoming.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Incoming.class, Incoming.class.getSimpleName() + "." + "getById");
    }
}