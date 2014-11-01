package io.betterlife.persistence.finical;

import io.betterlife.application.Config;
import io.betterlife.domains.finical.Expense;
import io.betterlife.persistence.BaseOperator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.Properties;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */

public class ExpenseOperator {

    private static final Log log = LogFactory.getLog(ExpenseOperator.class.getName());
    private static ExpenseOperator instance = new ExpenseOperator();

    private ExpenseOperator(){}

    public Expense get(EntityManager entityManager, long id) {
        return BaseOperator.getInstance().getBaseObject(entityManager, id, "getExpenseById");
    }


    public static ExpenseOperator getInstance() {
        return instance;
    }

    public static void setInstance(ExpenseOperator instance) {
        ExpenseOperator.instance = instance;
    }

    public void save(EntityManager entityManager, Expense expense) {
        entityManager.persist(expense);
    }
}
