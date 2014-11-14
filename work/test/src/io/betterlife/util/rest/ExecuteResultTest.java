package io.betterlife.util.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class ExecuteResultTest {
    @Test public void testSuccess() {
        ExecuteResult result = new ExecuteResult();
        assertEquals(true, result.isSuccess());
    }
}
