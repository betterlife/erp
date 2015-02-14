package io.betterlife.erp.trigger;

import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.ExpenseCategory;
import io.betterlife.erp.domains.order.PurchaseOrder;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.financial.DefaultFinancialSetting;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.trigger.EntityTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public class PurchaseOrderSaveTrigger extends ExpenseUpdater implements EntityTrigger {
    private static final Logger logger = LogManager.getLogger(PurchaseOrderSaveTrigger.class.getName());

    private List<Expense> originalExpenses = new ArrayList<>(4);

    @Override
    public void action(EntityManager em, BaseObject newObj, BaseObject oldObj) throws Exception {
        logger.debug("=======Invoking the PurchaseOrderSaveTrigger Start=====");
        if (newObj instanceof PurchaseOrder) {
            final PurchaseOrder purchaseOrder = (PurchaseOrder) newObj;
            PurchaseOrder originalPurchaseOrder = (PurchaseOrder) oldObj;
            if (originalPurchaseOrder != null) {
                originalExpenses = originalPurchaseOrder.getExpenses();
            }
            mergeExpense(
                em, purchaseOrder, getDefaultFinancialSetting().getDefPOLogisticExpCate(),
                purchaseOrder.getLogisticAmount(), "", false
            );
            mergeExpense(em, purchaseOrder, getDefaultFinancialSetting().getDefPOProductExpCate(),
                purchaseOrder.getAmount(), "", true);
            mergeExpense(em, purchaseOrder, getDefaultFinancialSetting().getDefPOOtherExpCate(),
                         purchaseOrder.getOtherAmount(), "", false);
        }
        logger.debug("=======Invoking the PurchaseOrderSaveTrigger End=====");
    }

    private void mergeExpense(EntityManager entityManager, PurchaseOrder purchaseOrder,
                              final ExpenseCategory defaultExpenseCate, final BigDecimal amount,
                              final String remarkStr, boolean throwException) throws Exception {
        if (null != amount) {
            Expense expense = updateExistingExpenses(originalExpenses, defaultExpenseCate, amount, purchaseOrder.getOrderDate());
            if (null == expense) {
                expense = createExpense(defaultExpenseCate, amount, remarkStr, purchaseOrder.getOrderDate(), purchaseOrder.getUser());
                expense.setPurchaseOrder(purchaseOrder);
            }
            saveExpense(entityManager, expense, purchaseOrder.getActive());
        } else {
            if (throwException) {
                throw new Exception("Amount should not be null for PO: " + purchaseOrder);
            }
        }
    }

}
