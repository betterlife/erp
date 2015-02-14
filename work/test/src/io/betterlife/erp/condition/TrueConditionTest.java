package io.betterlife.erp.condition;

import io.betterlife.framework.condition.TrueCondition;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrueConditionTest {

    @Test
    public void testEvaluate() throws Exception {
        assertTrue(new TrueCondition().evaluate(null, null, null, null));
    }
}