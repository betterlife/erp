package io.betterlife.persistence;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/24/14
 */
public class BaseOperatorTest {

    @Test
    public void testInit(){
        BaseOperator operator = BaseOperator.getInstance();
        assertNotNull(operator);
    }
}
