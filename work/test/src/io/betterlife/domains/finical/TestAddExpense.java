package io.betterlife.domains.finical;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.finical.ExpenseOperator;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class TestAddExpense {

    //TODO.xqliu 研究看Entity怎么继承,减少代码的重复

    @Test
    public void testAddValidExpense() {
        Expense expense = new Expense();
        expense.setCategory(new ExpenseCategory("Marketing"));
        expense.setCostCenter(new CostCenter("实体店"));
        final User user = new User();
        user.setUsername("xqliu");
        user.setPassword("xqliu");
        expense.setUser(user);
        expense.setAmount(new BigDecimal("100"));
        expense.setRemark("Remark");
        expense.setExpenseDate(new Date());
        expense.setLastModifyDate(new Date());
        expense.setLastModify(user);
        expense.setCreateDate(new Date());
        expense.setCreator(user);
        //ExpenseOperator.getInstance().save(expense);
        //Expense newExpense = ExpenseOperator.getInstance().get(entityManager, expense.getId());
        //Assert.assertEquals(expense, newExpense);
    }
}
