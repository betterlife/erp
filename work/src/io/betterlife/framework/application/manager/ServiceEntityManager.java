package io.betterlife.framework.application.manager;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.erp.domains.catalog.ProductCategory;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.erp.domains.financial.*;
import io.betterlife.erp.domains.order.PurchaseOrder;
import io.betterlife.erp.domains.order.SalesOrder;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.util.BLStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class ServiceEntityManager {
    private boolean serviceEntityRegistered = false;
    private final Map<String, Class<? extends BaseObject>> classes = new HashMap<>();
    private static ServiceEntityManager instance = new ServiceEntityManager();

    public static ServiceEntityManager getInstance() {
        return instance;
    }

    private ServiceEntityManager() {}

    public Class<? extends BaseObject> getServiceEntityClass(String name) {
        if (!serviceEntityRegistered) {
            registerEntities();
            serviceEntityRegistered = true;
        }
        return classes.get(name);
    }

    public synchronized void registerServiceEntity(String name, Class<? extends BaseObject> clazz) {
        classes.put(name, clazz);
    }

    public BaseObject entityObjectFromType(String objectType) throws InstantiationException, IllegalAccessException {
        Class<? extends BaseObject> clazz = getServiceEntityClass(objectType);
        return clazz.newInstance();
    }

    public Map<String, FieldMeta> getMetaFromEntityType(String entityType) {
        final Class<? extends BaseObject> entityClass = getServiceEntityClass(BLStringUtils.capitalize(entityType));
        return MetaDataManager.getInstance().getMetaDataOfClass(entityClass);
    }

    /** Register all entities, this registry will be used for
     *  Generate Entity CRUD rest services
     *  Generate meta data for creation of front end CRUD UI.
     */
    public static void registerEntities(){
        ServiceEntityManager.getInstance().registerServiceEntity("ExpenseCategory", ExpenseCategory.class);
        ServiceEntityManager.getInstance().registerServiceEntity("Expense", Expense.class);
        ServiceEntityManager.getInstance().registerServiceEntity("IncomingCategory", IncomingCategory.class);
        ServiceEntityManager.getInstance().registerServiceEntity("Incoming", Incoming.class);
        ServiceEntityManager.getInstance().registerServiceEntity("User", User.class);
        ServiceEntityManager.getInstance().registerServiceEntity("CostCenter", CostCenter.class);
        ServiceEntityManager.getInstance().registerServiceEntity("Product", Product.class);
        ServiceEntityManager.getInstance().registerServiceEntity("ProductCategory", ProductCategory.class);
        ServiceEntityManager.getInstance().registerServiceEntity("Supplier", Supplier.class);
        ServiceEntityManager.getInstance().registerServiceEntity("PurchaseOrder", PurchaseOrder.class);
        ServiceEntityManager.getInstance().registerServiceEntity("SalesOrder", SalesOrder.class);
    }

}