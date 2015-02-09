package io.betterlife.erp.trigger;

import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.ExpenseCategory;
import io.betterlife.erp.domains.order.PurchaseOrder;
import io.betterlife.framework.application.manager.SharedEntityManager;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.financial.DefaultFinancialSetting;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.trigger.EntityTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public class PurchaseOrderSaveTrigger implements EntityTrigger {
    private static final Logger logger = LogManager.getLogger(PurchaseOrderSaveTrigger.class.getName());

    private PurchaseOrder originalPurchaseOrder;

    @Override
    public void action(EntityManager em, BaseObject baseObject) throws Exception {
        logger.debug("=======Invoking the PurchaseOrderSaveTrigger Start=====");
        final String query = NamedQueryRules.getInstance().getAllQueryForEntity("DefaultFinancialSetting");
        final BaseOperator baseOpera = BaseOperator.getInstance();
        DefaultFinancialSetting setting = baseOpera.getSingleResult(baseOpera.getQuery(query));
        if (baseObject instanceof PurchaseOrder) {
            final PurchaseOrder purchaseOrder = (PurchaseOrder) baseObject;
            final long id = baseObject.getId();
            originalPurchaseOrder = getOriginalPurchaseOrder(id);
            mergeExpense(
                em, purchaseOrder, setting.getDefPOLogisticExpCate(), purchaseOrder.getLogisticAmount(),
                "Logistic expense for PurchaseOrder " + id, false
            );
            mergeExpense(
                em, purchaseOrder, setting.getDefPOProductExpCate(), purchaseOrder.getAmount(),
                "Product expense for PurchaseOrder " + id, true
            );
            mergeExpense(
                em, purchaseOrder, setting.getDefPOOtherExpCate(), purchaseOrder.getOtherAmount(),
                "Other expense for PurchaseOrder " + purchaseOrder.getId(), false
            );
        }
        logger.debug("=======Invoking the PurchaseOrderSaveTrigger End=====");
    }

    private void mergeExpense(EntityManager entityManager, PurchaseOrder baseObject,
                              final ExpenseCategory defaultExpenseCate, final BigDecimal amount,
                              final String remarkStr, boolean throwException) throws Exception {
        if (null != amount) {
            Expense expense = null;
            List<Expense> expenses = originalPurchaseOrder.getExpenses();
            for (Expense e : expenses) {
                if (e.getActive()) {
                    final ExpenseCategory expenseCategory = e.getExpenseCategory();
                    if (null != expenseCategory && expenseCategory.equals(defaultExpenseCate)) {
                        expense = e;
                        updateExpense(baseObject, expense, amount);
                        break;
                    }
                }
            }
            if (null == expense) {
                expense = createExpense(baseObject, amount, defaultExpenseCate, remarkStr);
            }
            expense.setActive(baseObject.getActive());
            entityManager.merge(expense);
        } else {
            if (throwException) {
                throw new Exception("Amount should not be null for PO: " + baseObject);
            }
        }
    }

    private PurchaseOrder getOriginalPurchaseOrder(long id) {
        String queryName = NamedQueryRules.getInstance().getIdQueryForEntity(PurchaseOrder.class.getSimpleName());
        EntityManager entityManager = SharedEntityManager.getInstance().getEntityManager();
        Query q = entityManager.createNamedQuery(queryName);
        q.setHint(QueryHints.REFRESH, HintValues.TRUE);
        q.setParameter("id", id);
        return (PurchaseOrder) q.getSingleResult();
    }

    private void updateExpense(PurchaseOrder baseObject, Expense existing, BigDecimal bd) {
        existing.setAmount(bd);
        existing.setDate(baseObject.getOrderDate());
    }

    private Expense createExpense(PurchaseOrder baseObject, BigDecimal bd, ExpenseCategory expCate, String remarkStr) {
        Expense expense = new Expense();
        expense.setAmount(bd);
        expense.setDate(baseObject.getOrderDate());
        expense.setPurchaseOrder(baseObject);
        expense.setExpenseCategory(expCate);
        expense.setUser(baseObject.getUser());
        expense.setCreateDate(new Date());
        expense.setActive(true);
        expense.setRemark(remarkStr);
        return expense;
    }
}
