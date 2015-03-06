package io.betterlife.framework.persistence;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/24/14
 */
public class BaseOperatorTest {

    @Test
    public void testGetInstance() throws Exception {
        BaseOperator operator = BaseOperator.getInstance();
        assertNotNull(operator);
    }

    @Test
    public void testSetInstance() throws Exception {
        BaseOperator baseOperator = Mockito.mock(BaseOperator.class);
        BaseOperator.setInstance(baseOperator);
        assertNotNull(BaseOperator.getInstance());
        assertSame(baseOperator, BaseOperator.getInstance());
    }

    @Test
    public void testGetBaseObjectById() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testSaveBaseObjectWithTransaction() throws Exception {

    }

    @Test
    public void testGetBaseObject() throws Exception {

    }

    @Test
    public void testGetBaseObjects() throws Exception {

    }

    @Test
    public void testGetQuery() throws Exception {

    }

    @Test
    public void testGetSingleResult() throws Exception {

    }
}
