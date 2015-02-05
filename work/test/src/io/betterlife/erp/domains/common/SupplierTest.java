package io.betterlife.erp.domains.common;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

import static org.junit.Assert.*;

public class SupplierTest {

    private Supplier supplier;

    @Before
    public void setUp() throws Exception {
        supplier = new Supplier();
    }

    @Test
    public void testSetName() throws Exception {
        final String name = "A Supplier Name";
        supplier.setName(name);
        final String newName = "New Supplier Name";
        supplier.setName(newName);
        assertNotNull(supplier.getName());
        assertNotEquals(name, supplier.getName());
        assertSame(newName, supplier.getName());
    }

    @Test
    public void testGetName() throws Exception {
        supplier = new Supplier();
        final String name = "A Supplier Name";
        supplier.setName(name);
        assertNotNull(supplier.getName());
        assertSame(name, supplier.getName());
    }

    @Test
    public void testSetContact() throws Exception {
        supplier = new Supplier();
        final String contact = "A Supplier Contact Name";
        supplier.setContact(contact);
        final String newContact = "New Supplier Contact Name";
        supplier.setContact(newContact);
        assertNotNull(supplier.getContact());
        assertNotEquals(contact, supplier.getContact());
        assertSame(newContact, supplier.getContact());
    }

    @Test
    public void testGetContact() throws Exception {
        supplier = new Supplier();
        final String contact = "A Supplier Contact Name";
        supplier.setContact(contact);
        assertNotNull(supplier.getContact());
        assertSame(contact, supplier.getContact());
    }

    @Test
    public void testGetEmail() throws Exception {
        supplier = new Supplier();
        final String email = "abc@betterlife.io";
        supplier.setEmail(email);
        assertNotNull(supplier.getEmail());
        assertSame(email, supplier.getEmail());
    }

    @Test
    public void testSetEmail() throws Exception {
        supplier = new Supplier();
        final String email = "abc@betterlife.io";
        supplier.setEmail(email);
        final String newEmail = "def@betterlife.io";
        supplier.setEmail(newEmail);
        assertNotNull(supplier.getEmail());
        assertNotEquals(email, supplier.getEmail());
        assertSame(newEmail, supplier.getEmail());
    }

    @Test
    public void testGetPhone() throws Exception {
        supplier = new Supplier();
        final String phone = "1380000000";
        supplier.setPhone(phone);
        assertNotNull(supplier.getPhone());
        assertSame(phone, supplier.getPhone());
    }

    @Test
    public void testSetPhone() throws Exception {
        supplier = new Supplier();
        final String phone = "13800000";
        supplier.setPhone(phone);
        final String newPhone = "13000000";
        supplier.setPhone(newPhone);
        assertNotNull(supplier.getPhone());
        assertNotEquals(phone, supplier.getPhone());
        assertSame(newPhone, supplier.getPhone());
    }

    @Test
    public void testGetQq() throws Exception {
        supplier = new Supplier();
        final String qq = "123456789";
        supplier.setQq(qq);
        assertNotNull(supplier.getQq());
        assertSame(qq, supplier.getQq());
    }

    @Test
    public void testSetQq() throws Exception {
        supplier = new Supplier();
        final String qq = "13800000";
        supplier.setQq(qq);
        final String newQQ = "13000000";
        supplier.setQq(newQQ);
        assertNotNull(supplier.getQq());
        assertNotEquals(qq, supplier.getQq());
        assertSame(newQQ, supplier.getQq());
    }

    @Test
    public void testGetWebsite() throws Exception {
        supplier = new Supplier();
        final String website = "http://betterlife.io";
        supplier.setWebsite(website);
        assertNotNull(supplier.getWebsite());
        assertSame(website, supplier.getWebsite());
    }

    @Test
    public void testSetWebsite() throws Exception {
        supplier = new Supplier();
        final String website = "13800000";
        supplier.setWebsite(website);
        final String newWebsite = "13000000";
        supplier.setWebsite(newWebsite);
        assertNotNull(supplier.getWebsite());
        assertNotEquals(website, supplier.getWebsite());
        assertSame(newWebsite, supplier.getWebsite());
    }

    @Test
    public void testGetWholesaleRequirement() throws Exception {
        supplier = new Supplier();
        final String wholesaleRequirement = "一万起批";
        supplier.setWholesaleRequirement(wholesaleRequirement);
        assertNotNull(supplier.getWholesaleRequirement());
        assertSame(wholesaleRequirement, supplier.getWholesaleRequirement());
    }

    @Test
    public void testSetWholesaleRequirement() throws Exception {
        supplier = new Supplier();
        final String wholesaleRequirement = "一万起批";
        supplier.setWholesaleRequirement(wholesaleRequirement);
        final String newWholesaleRequirement = "一万五起批";
        supplier.setWholesaleRequirement(newWholesaleRequirement);
        assertNotNull(supplier.getWholesaleRequirement());
        assertNotEquals(wholesaleRequirement, supplier.getWholesaleRequirement());
        assertSame(newWholesaleRequirement, supplier.getWholesaleRequirement());
    }

    @Test
    public void testGetCanMixedWholesale() throws Exception {
        supplier = new Supplier();
        final boolean canMixedWholesale = false;
        supplier.setCanMixedWholesale(canMixedWholesale);
        assertNotNull(supplier.getCanMixedWholesale());
        assertSame(canMixedWholesale, supplier.getCanMixedWholesale());
    }

    @Test
    public void testSetCanMixedWholesale() throws Exception {
        supplier = new Supplier();
        final boolean canMixedWholesale = true;
        supplier.setCanMixedWholesale(canMixedWholesale);
        final boolean newCanMixedWholesale = false;
        supplier.setCanMixedWholesale(newCanMixedWholesale);
        assertNotNull(supplier.getCanMixedWholesale());
        assertNotEquals(canMixedWholesale, supplier.getCanMixedWholesale());
        assertSame(newCanMixedWholesale, supplier.getCanMixedWholesale());
    }

    @Test
    public void testGetRemark() throws Exception {
        supplier = new Supplier();
        final String remark = "物流费包括在批发价格中";
        supplier.setRemark(remark);
        assertNotNull(supplier.getRemark());
        assertSame(remark, supplier.getRemark());
    }

    @Test
    public void testSetRemark() throws Exception {
        supplier = new Supplier();
        final String remark = "物流费包括在批发价格中";
        supplier.setRemark(remark);
        final String newRemark = "物流费不包括在批发价格中";
        supplier.setRemark(newRemark);
        assertNotNull(supplier.getRemark());
        assertNotEquals(remark, supplier.getRemark());
        assertSame(newRemark, supplier.getRemark());
    }

    @Test
    public void testRemarkSize() {
        supplier = new Supplier();
        byte[] b = new byte[512];
        String str = new String(b);
        supplier = new Supplier();
        supplier.setRemark(str);
        assertNotNull(supplier.getRemark());
        assertSame(str, supplier.getRemark());
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity entity = Supplier.class.getAnnotation(Entity.class);
        assertNotNull(entity);
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Supplier.class, Supplier.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(Supplier.class, Supplier.class.getSimpleName() + "." + "getById");
    }
}