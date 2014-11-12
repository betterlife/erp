package io.betterlife.application;

import io.betterlife.domains.financial.CostCenter;
import io.betterlife.domains.financial.Expense;
import io.betterlife.domains.financial.ExpenseCategory;
import io.betterlife.domains.security.User;
import io.betterlife.rest.EntityService;

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

    /** Register all entities, this registry will be used for
     *  Generate Entity CRUD rest services
     *  Generate meta data for creation of front end CRUD UI.
     */
    public static void registerEntities(){
        EntityService.registerServiceEntity("expenseCategory", ExpenseCategory.class);
        EntityService.registerServiceEntity("user", User.class);
        EntityService.registerServiceEntity("costCenter", CostCenter.class);
        EntityService.registerServiceEntity("expense", Expense.class);
    }
}