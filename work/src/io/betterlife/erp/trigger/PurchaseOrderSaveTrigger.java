package io.betterlife.erp.trigger;

import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.order.PurchaseOrder;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.trigger.EntityTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public class PurchaseOrderSaveTrigger implements EntityTrigger {
    private static final Logger logger = LogManager.getLogger(PurchaseOrderSaveTrigger.class.getName());

    @Override
    public void action(EntityManager entityManager, BaseObject baseObject) throws Exception {
        logger.debug("=======I am the PurchaseOrderSaveTrigger");
        logger.debug(baseObject);
        if (baseObject instanceof PurchaseOrder) {
            createLogisticExpense(entityManager, (PurchaseOrder) baseObject);
            createPurchaseExpense(entityManager, (PurchaseOrder) baseObject);
            createOtherExpenseIfAny(entityManager, (PurchaseOrder) baseObject);
        }
    }

    private void createOtherExpenseIfAny(EntityManager entityManager, PurchaseOrder baseObject) {
        BigDecimal bd = baseObject.getOtherAmount();
        if (null != bd) {
            Expense expense = saveExpense(baseObject, bd);
            expense.setRemark("Other expense for PurchaseOrder " + baseObject.getId());
            entityManager.merge(expense);
        }
    }

    private void createPurchaseExpense(EntityManager entityManager, PurchaseOrder baseObject) throws Exception {
        BigDecimal bd = baseObject.getAmount();
        if (null != bd) {
            Expense expense = saveExpense(baseObject, bd);
            expense.setRemark("Product expense for PurchaseOrder " + baseObject.getId());
            entityManager.merge(expense);
        } else {
            throw new Exception("Amount in purchase Order " + baseObject + " is null, this should not happen");
        }
    }

    private void createLogisticExpense(EntityManager entityManager, PurchaseOrder baseObject) throws Exception {
        BigDecimal bd = baseObject.getLogisticAmount();
        if (null != bd) {
            Expense expense = saveExpense(baseObject, bd);
            expense.setRemark("Product expense for PurchaseOrder " + baseObject.getId());
            entityManager.merge(expense);
        } else {
            logger.warn("Logistic Amount in purchase Order " + baseObject + " is null, this might be an error");
        }
    }

    private Expense saveExpense(PurchaseOrder baseObject, BigDecimal bd) {
        Expense expense = new Expense();
        expense.setAmount(bd);
        expense.setDate(baseObject.getOrderDate());
        expense.setPurchaseOrder(baseObject);
        expense.setUser(baseObject.getUser());
        expense.setCreateDate(new Date());
        expense.setActive(true);
        return expense;
    }
}
