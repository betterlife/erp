package io.betterlife.domains.financial;

import io.betterlife.domains.security.User;
import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FundTest {

    private Fund fund;

    @Before
    public void setUp() {
        fund = new Fund();
    }

    @Test
    public void testSetExpenseCategory() throws Exception {
        FundCategory category = mock(FundCategory.class);
        fund.setFundCategory(category);
        FundCategory category1 = mock(FundCategory.class);
        fund.setFundCategory(category1);
        final FundCategory fundCategory = fund.getFundCategory();
        assertNotNull(fundCategory);
        assertNotEquals(category, fundCategory);
        assertSame(category1, fundCategory);
    }

    @Test
    public void testGetExpenseCategory() throws Exception {
        FundCategory category = mock(FundCategory.class);
        fund.setFundCategory(category);
        final FundCategory fundCategory = fund.getFundCategory();
        assertNotNull(fundCategory);
        assertSame(category, fundCategory);
    }

    @Test
    public void testSetCostCenter() throws Exception {
        CostCenter cc = mock(CostCenter.class);
        fund.setCostCenter(cc);
        CostCenter cc1 = mock(CostCenter.class);
        fund.setCostCenter(cc1);
        final CostCenter costCenter = fund.getCostCenter();
        assertNotNull(costCenter);
        assertNotEquals(cc, costCenter);
        assertSame(cc1, costCenter);
    }

    @Test
    public void testGetCostCenter() throws Exception {
        CostCenter cc = mock(CostCenter.class);
        fund.setCostCenter(cc);
        final CostCenter costCenter = fund.getCostCenter();
        assertNotNull(costCenter);
        assertSame(cc, costCenter);
    }

    @Test
    public void testSetUser() throws Exception {
        User user = mock(User.class);
        fund.setUser(user);
        User user1 = mock(User.class);
        fund.setUser(user1);
        final User user2 = fund.getUser();
        assertNotNull(user2);
        assertNotEquals(user, user2);
        assertSame(user1, user2);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = mock(User.class);
        fund.setUser(user);
        final User user1 = fund.getUser();
        assertNotNull(user1);
        assertSame(user, user1);
    }

    @Test
    public void testSetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        fund.setAmount(bd);
        BigDecimal bd1 = new BigDecimal("20.90");
        fund.setAmount(bd1);
        final BigDecimal amount = fund.getAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("0.01");
        fund.setAmount(bd);
        final BigDecimal amount = fund.getAmount();
        assertNotNull(amount);
        assertSame(bd, amount);
    }

    @Test
    public void testSetRemark() throws Exception {
        String str = "I am a remark with 中文";
        fund.setRemark(str);
        String str1 = "I am a 新的 remark with 中文";
        fund.setRemark(str1);
        final String remark = fund.getRemark();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }

    @Test
    public void testGetRemark() throws Exception {
        String str = "I am a remark with 中文";
        fund.setRemark(str);
        final String remark = fund.getRemark();
        assertNotNull(remark);
        assertSame(str, remark);
    }

    @Test
    public void testSetDate() throws Exception {
        Date date = new Date();
        fund.setDate(date);
        Date date1 = new Date();
        fund.setDate(date1);
        final Date date2 = fund.getDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testGetDate() throws Exception {
        Date date = new Date();
        fund.setDate(date);
        final Date date1 = fund.getDate();
        assertNotNull(date1);
        assertSame(date, date1);
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = Fund.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Fund.class, Fund.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Fund.class, Fund.class.getSimpleName() + "." + "getById");
    }
}