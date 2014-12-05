package io.betterlife.domains;

import io.betterlife.domains.financial.Expense;
import io.betterlife.domains.security.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    }

    @Test
    public void testSetCreateDate() throws Exception {

    }

    @Test
    public void testGetCreateDate() throws Exception {

    }

    @Test
    public void testSetCreator() throws Exception {

    }

    @Test
    public void testGetCreator() throws Exception {

    }

    @Test
    public void testSetValues() throws Exception {

    }
}