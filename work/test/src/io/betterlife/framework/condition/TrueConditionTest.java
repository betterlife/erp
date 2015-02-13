package io.betterlife.framework.condition;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TrueConditionTest {

    @Test
    public void testEvaluate() throws Exception {
        assertTrue(new TrueCondition().evaluate(null, null, null, null));
    }
}