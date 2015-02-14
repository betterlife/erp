package io.betterlife.framework.condition;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FalseConditionTest {

    @Test
    public void testEvaluate() throws Exception {
        assertFalse(new FalseCondition().evaluate(null, null, null, null));
    }
}