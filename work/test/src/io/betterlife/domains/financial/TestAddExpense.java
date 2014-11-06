package io.betterlife.domains.financial;

import org.junit.Test;

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
