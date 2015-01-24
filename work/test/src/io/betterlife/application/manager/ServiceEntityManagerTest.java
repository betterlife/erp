package io.betterlife.application.manager;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.financial.CostCenter;
import io.betterlife.domains.financial.Expense;
import io.betterlife.domains.financial.ExpenseCategory;
import io.betterlife.domains.security.User;
import io.betterlife.util.EntityMockUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceEntityManagerTest {

    private ServiceEntityManager serviceEntityManager;

    @Before
    public void setUp() {
        this.serviceEntityManager = ServiceEntityManager.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(ServiceEntityManager.getInstance());
    }

    @Test
    public void testGetServiceEntityClass() throws Exception {
        assertEquals(ExpenseCategory.class, serviceEntityManager.getServiceEntityClass("ExpenseCategory"));
        assertEquals(Expense.class, serviceEntityManager.getServiceEntityClass("Expense"));
        assertEquals(CostCenter.class, serviceEntityManager.getServiceEntityClass("CostCenter"));
        assertEquals(User.class, serviceEntityManager.getServiceEntityClass("User"));
    }

    @Test
    public void testRegisterServiceEntity() throws Exception {
        serviceEntityManager.registerServiceEntity("DummyClass", DummyClass.class);
        assertEquals(DummyClass.class, serviceEntityManager.getServiceEntityClass("DummyClass"));
    }

    @Test
    public void testEntityObjectFromType() throws Exception {
        serviceEntityManager.registerServiceEntity("DummyClass", DummyClass.class);
        BaseObject baseObject = serviceEntityManager.entityObjectFromType("DummyClass");
        assertNotNull(baseObject);
        assertEquals(DummyClass.class, baseObject.getClass());
    }

    @Test
    public void testGetMetaFromEntityType() throws Exception {
        SharedEntityManager sharedEntityManager = mock(SharedEntityManager.class);
        EntityManager manager = EntityMockUtil.getInstance().mockEntityManagerAndMeta();
        when(sharedEntityManager.getEntityManager()).thenReturn(manager);
        Mockito.doNothing().when(sharedEntityManager).close();
        MetaDataManager.getInstance().setSharedEntityManager(sharedEntityManager);
        Map<String, FieldMeta> result = serviceEntityManager.getMetaFromEntityType("User");
        assertNotNull(result);
        assertEquals(3, result.size());
        verifyFieldMeta(result, "id", Long.class);
        verifyFieldMeta(result, "username", String.class);
        verifyFieldMeta(result, "password", String.class);
    }

    public void verifyFieldMeta(Map<String, FieldMeta> result, final String fieldName, final Class fieldType) {
        assertTrue(result.containsKey(fieldName));
        assertEquals(fieldType, result.get(fieldName).getType());
    }
}

class DummyClass extends BaseObject {
    public DummyClass() {
    }
};
