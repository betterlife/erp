package io.betterlife.application;

import io.betterlife.domains.financial.CostCenter;
import io.betterlife.domains.financial.Expense;
import io.betterlife.domains.financial.ExpenseCategory;
import io.betterlife.domains.security.User;
import io.betterlife.rest.BaseService;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 * Application config
 */
public class ApplicationConfig {

    /**
     * Application data source name
     */
    public static final String DataSourceName = "jdbc/betterlife-sqld";

    /**
     * Application persistence unit name
     */
    public static final String PersistenceUnitName = "betterlife";

    /**
     * Table name prefix
     */
    public static final String TableNamePrefix = "BL_";

    public static void registerEntities(){
        BaseService.registerServiceEntity("ExpenseCategory", ExpenseCategory.class);
        BaseService.registerServiceEntity("User", User.class);
        BaseService.registerServiceEntity("CostCenter", CostCenter.class);
        BaseService.registerServiceEntity("Expense", Expense.class);
    }
}
