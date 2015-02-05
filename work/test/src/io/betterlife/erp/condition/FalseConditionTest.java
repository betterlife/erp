package io.betterlife.erp.condition;

import io.betterlife.framework.condition.FalseCondition;
import org.junit.Test;

import static org.junit.Assert.*;

public class FalseConditionTest {

    @Test
    public void testEvaluate() throws Exception {
        assertFalse(new FalseCondition().evaluate(null, null, null, null));
    }
}