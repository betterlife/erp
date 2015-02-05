package io.betterlife.erp.domains;

import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.domains.BaseObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

class MockBaseObject extends BaseObject {

}

public class BaseObjectTest {

    private BaseObject baseObject;

    @Before
    public void setUp() throws Exception {
        baseObject = new MockBaseObject();
    }

    @Test
    public void testIdIs0ForNewObject() throws Exception {
        assertNotNull(baseObject.getId());
        assertEquals(0, baseObject.getId());
    }

    @Test
    public void testSetIdManually() throws Exception {
        baseObject.setId(10L);
        assertEquals(10L, baseObject.getId());
    }

    @Test
    public void testGetAndSetValue() throws Exception {
        final String fieldName = "I_AM_A_DUMMY_FIELD";
        final String fieldValue = "I_AM_A_DUMMY_VALUE";
        baseObject.setValue(fieldName, fieldValue);
        String result = baseObject.getValue(fieldName);
        assertSame(fieldValue, result);
    }

    @Test
    public void testFieldLastModifyDate() throws Exception {
        final Date date = new Date();
        baseObject.setLastModifyDate(date);
        assertSame(date, baseObject.getLastModifyDate());
    }

    @Test
    public void testFieldLastModify() throws Exception {
        User user = new User();
        baseObject.setLastModify(user);
        assertSame(user, baseObject.getLastModify());
    }

    @Test
    public void testCreateDate() throws Exception {
        final Date date = new Date();
        baseObject.setCreateDate(date);
        assertSame(date, baseObject.getCreateDate());
    }

    @Test
    public void testCreator() throws Exception {
        User user = new User();
        baseObject.setCreator(user);
        assertSame(user, baseObject.getCreator());
    }

    @Test
    public void testGetActive() {
        baseObject.setActive(true);
        assertTrue(baseObject.getActive());
        baseObject.setActive(false);
        assertFalse(baseObject.getActive());
    }

    @Test
    public void testSetValues() throws Exception {

    }
}