package io.betterlife.erp.trigger;

import io.betterlife.erp.domains.financial.DefaultFinancialSetting;
import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.ExpenseCategory;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 15/2/10
 */
public class ExpenseUpdater {

    private static DefaultFinancialSetting setting;

    protected static DefaultFinancialSetting getDefaultFinancialSetting() {
        if (null == setting) {
            final String query = NamedQueryRules.getInstance().getAllQueryForEntity("DefaultFinancialSetting");
            final BaseOperator baseOpera = BaseOperator.getInstance();
            setting = baseOpera.getSingleResult(baseOpera.getQuery(query));
        }
        return setting;
    }

    protected Expense createExpense(ExpenseCategory expCate, BigDecimal amount,
                                    String remarkStr, final Date orderDate, final User user) {
        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setDate(orderDate);
        expense.setExpenseCategory(expCate);
        expense.setUser(user);
        expense.setCreateDate(new Date());
        expense.setActive(true);
        expense.setRemark(remarkStr);
        return expense;
    }

    protected Expense updateExistingExpenses(List<Expense> expenses, ExpenseCategory defaultExpenseCate,
                                             BigDecimal amount, final Date orderDate) {
        Expense expense = null;
        for (Expense e : expenses) {
            if (e.getActive()) {
                final ExpenseCategory expenseCategory = e.getExpenseCategory();
                if (null != expenseCategory && expenseCategory.equals(defaultExpenseCate)) {
                    expense = e;
                    expense.setAmount(amount);
                    expense.setDate(orderDate);
                    break;
                }
            }
        }
        return expense;
    }

    protected void saveExpense(EntityManager entityManager, Expense expense, final boolean active) {
        expense.setActive(active);
        entityManager.merge(expense);
    }
}
