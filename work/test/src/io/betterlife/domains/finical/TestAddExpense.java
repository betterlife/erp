package io.betterlife.domains.finical;

import io.betterlife.domains.security.User;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class TestAddExpense {

    @Test
    public void testAddValidExpenseCategory() {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setCategoryName("测试Category");
    }
}
