package io.betterlife.erp.domains.financial;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

import static org.junit.Assert.*;

public class CostCenterTest {

    CostCenter costCenter;

    @Before
    public void setUp() throws Exception {
        costCenter = new CostCenter();
    }

    @Test
    public void testConstructorWithParam() {
        final String name = "Cost Center Name";
        CostCenter cc = new CostCenter(name);
        assertNotNull(cc);
        assertSame(name, cc.getName());
    }

    @Test
    public void testSetName() throws Exception {
        costCenter = new CostCenter();
        final String name = "A Cost Center Name";
        costCenter.setName(name);
        final String newName = "New Cost Center Name";
        costCenter.setName(newName);
        assertNotNull(costCenter.getName());
        assertNotEquals(name, costCenter.getName());
        assertSame(newName, costCenter.getName());
    }

    @Test
    public void testGetName() throws Exception {
        costCenter = new CostCenter();
        final String name = "A Cost Center Name";
        costCenter.setName(name);
        assertNotNull(costCenter.getName());
        assertSame(name, costCenter.getName());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = CostCenter.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(CostCenter.class, CostCenter.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(CostCenter.class, CostCenter.class.getSimpleName() + "." + "getById");
    }
}