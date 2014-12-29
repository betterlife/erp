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

public class ExpenseTest {

    private Expense expense;

    @Before
    public void setUp() {
        expense = new Expense();
    }

    @Test
    public void testSetExpenseCategory() throws Exception {
        ExpenseCategory category = mock(ExpenseCategory.class);
        expense.setExpenseCategory(category);
        ExpenseCategory category1 = mock(ExpenseCategory.class);
        expense.setExpenseCategory(category1);
        final ExpenseCategory expenseCategory = expense.getExpenseCategory();
        assertNotNull(expenseCategory);
        assertNotEquals(category, expenseCategory);
        assertSame(category1, expenseCategory);
    }

    @Test
    public void testGetExpenseCategory() throws Exception {
        ExpenseCategory category = mock(ExpenseCategory.class);
        expense.setExpenseCategory(category);
        final ExpenseCategory expenseCategory = expense.getExpenseCategory();
        assertNotNull(expenseCategory);
        assertSame(category, expenseCategory);
    }

    @Test
    public void testSetCostCenter() throws Exception {
        CostCenter cc = mock(CostCenter.class);
        expense.setCostCenter(cc);
        CostCenter cc1 = mock(CostCenter.class);
        expense.setCostCenter(cc1);
        final CostCenter costCenter = expense.getCostCenter();
        assertNotNull(costCenter);
        assertNotEquals(cc, costCenter);
        assertSame(cc1, costCenter);
    }

    @Test
    public void testGetCostCenter() throws Exception {
        CostCenter cc = mock(CostCenter.class);
        expense.setCostCenter(cc);
        final CostCenter costCenter = expense.getCostCenter();
        assertNotNull(costCenter);
        assertSame(cc, costCenter);
    }

    @Test
    public void testSetUser() throws Exception {
        User user = mock(User.class);
        expense.setUser(user);
        User user1 = mock(User.class);
        expense.setUser(user1);
        final User user2 = expense.getUser();
        assertNotNull(user2);
        assertNotEquals(user, user2);
        assertSame(user1, user2);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = mock(User.class);
        expense.setUser(user);
        final User user1 = expense.getUser();
        assertNotNull(user1);
        assertSame(user, user1);
    }

    @Test
    public void testSetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("10.05");
        expense.setAmount(bd);
        BigDecimal bd1 = new BigDecimal("20.90");
        expense.setAmount(bd1);
        final BigDecimal amount = expense.getAmount();
        assertNotNull(amount);
        assertNotEquals(bd, amount);
        assertSame(bd1, amount);
    }

    @Test
    public void testGetAmount() throws Exception {
        BigDecimal bd = new BigDecimal("0.01");
        expense.setAmount(bd);
        final BigDecimal amount = expense.getAmount();
        assertNotNull(amount);
        assertSame(bd, amount);
    }

    @Test
    public void testSetRemark() throws Exception {
        String str = "I am a remark with 中文";
        expense.setRemark(str);
        String str1 = "I am a 新的 remark with 中文";
        expense.setRemark(str1);
        final String remark = expense.getRemark();
        assertNotNull(remark);
        assertNotEquals(str, remark);
        assertSame(str1, remark);
    }

    @Test
    public void testGetRemark() throws Exception {
        String str = "I am a remark with 中文";
        expense.setRemark(str);
        final String remark = expense.getRemark();
        assertNotNull(remark);
        assertSame(str, remark);
    }

    @Test
    public void testSetDate() throws Exception {
        Date date = new Date();
        expense.setDate(date);
        Date date1 = new Date();
        expense.setDate(date1);
        final Date date2 = expense.getDate();
        assertNotNull(date2);
        assertNotSame(date, date2);
        assertSame(date1, date2);
    }

    @Test
    public void testGetDate() throws Exception {
        Date date = new Date();
        expense.setDate(date);
        final Date date1 = expense.getDate();
        assertNotNull(date1);
        assertSame(date, date1);
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = Expense.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Expense.class, Expense.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Expense.class, Expense.class.getSimpleName() + "." + "getById");
    }
}