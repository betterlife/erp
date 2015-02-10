package io.betterlife.erp.trigger;

import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.ExpenseCategory;
import io.betterlife.erp.domains.financial.Incoming;
import io.betterlife.erp.domains.financial.IncomingCategory;
import io.betterlife.erp.domains.order.SalesOrder;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.trigger.EntityTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 15/2/10
 */
public class SalesOrderSaveTrigger extends ExpenseUpdater implements EntityTrigger {

    private static final Logger logger = LogManager.getLogger(SalesOrderSaveTrigger.class.getName());

    private SalesOrder originalSalesOrder = null;

    @Override
    public void action(EntityManager entityManager, BaseObject newObj, BaseObject oldObj) throws Exception {
        logger.debug("=======Invoking the SalesOrderSaveTrigger Start=====");
        if (newObj instanceof SalesOrder) {
            final SalesOrder salesOrder = (SalesOrder) newObj;
            originalSalesOrder = (SalesOrder) oldObj;
            mergeExpense(entityManager, salesOrder, getDefaultFinancialSetting().getDefSOLogisticExpCate(),
                         salesOrder.getLogisticAmount(), "", false);
            mergeIncoming(entityManager, salesOrder, getDefaultFinancialSetting().getDefSOIncomingCate(), "", true);
        }
        logger.debug("=======Invoking the SalesOrderSaveTrigger End=====");
    }

    private void mergeExpense(EntityManager entityManager, SalesOrder salesOrder,
                              final ExpenseCategory defaultExpenseCate, final BigDecimal amount,
                              final String remarkStr, boolean throwException) throws Exception {
        if (null != amount) {
            Expense expense = null;
            if (originalSalesOrder != null) {
                List<Expense> expenses = originalSalesOrder.getExpenses();
                expense = updateExistingExpenses(expenses, defaultExpenseCate, amount, salesOrder.getOrderDate());
            }
            if (null == expense) {
                expense = createExpense(defaultExpenseCate, amount, remarkStr, salesOrder.getOrderDate(), salesOrder.getTransactor());
                expense.setSalesOrder(salesOrder);
            }
            saveExpense(entityManager, expense, salesOrder.getActive());
        } else {
            if (throwException) {
                throw new Exception("Amount should not be null for SO: " + salesOrder);
            }
        }
    }

    private void mergeIncoming(EntityManager entityManager, SalesOrder salesOrder,
                               final IncomingCategory defaultIncomingCate,
                               final String remarkStr, boolean throwException) throws Exception {
        final BigDecimal amount = salesOrder.getAmount();
        if (null != amount) {
            final Date orderDate = salesOrder.getOrderDate();
            Incoming incoming = (originalSalesOrder != null) ? originalSalesOrder.getIncoming() : new Incoming();
            incoming.setAmount(amount);
            incoming.setDate(orderDate);
            incoming.setIncomingCategory(defaultIncomingCate);
            incoming.setCreateDate(new Date());
            incoming.setActive(true);
            incoming.setRemark(remarkStr);
            incoming.setSalesOrder(salesOrder);
            incoming.setActive(salesOrder.getActive());
            entityManager.merge(incoming);
        } else {
            if (throwException) {
                throw new Exception("Incoming amount should not be null for SO: " + salesOrder);
            }
        }
    }

}
