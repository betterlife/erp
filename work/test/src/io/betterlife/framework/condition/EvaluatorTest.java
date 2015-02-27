package io.betterlife.framework.condition;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.meta.FieldMeta;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockFalseCondition implements Condition {

    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return false;
    }
}

class MockTrueCondition implements Condition {

    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return true;
    }
}
public class EvaluatorTest {

    Evaluator evaluator = Evaluator.getInstance();

    @Test
    public void testEvalFalse() throws Exception {
        internalTestEval(mock(MockFalseCondition.class), false);
    }

    @Test
    public void testEvalTrue() throws Exception {
        internalTestEval(mock(MockTrueCondition.class), true);
    }

    private void internalTestEval(final Condition condition, final boolean expected) {
        String entityType = "User";
        BaseObject baseObject = null;
        FieldMeta fieldMeta = new FieldMeta();
        String operationType = "Update";
        boolean result = evaluator.eval(condition.getClass(), entityType, baseObject, fieldMeta, operationType);
        assertEquals(expected, result);
    }

    @Test
    public void testEvalVisible() throws Exception {
        String entityType = "User";
        BaseObject baseObject = null;
        String operationType = "Update";
        FieldMeta fieldMeta = mock(FieldMeta.class);
        Class clazz = MockFalseCondition.class;
        when(fieldMeta.getVisibleCondition()).thenReturn(clazz);
        boolean result = evaluator.evalVisible(entityType, fieldMeta, baseObject, operationType);
        assertFalse(result);
    }

    @Test
    public void testEvalEditable() throws Exception {
        String entityType = "User";
        BaseObject baseObject = null;
        String operationType = "Update";
        FieldMeta fieldMeta = mock(FieldMeta.class);
        Class clazz = MockTrueCondition.class;
        when(fieldMeta.getEditableCondition()).thenReturn(clazz);
        boolean result = evaluator.evalEditable(entityType, fieldMeta, baseObject, operationType);
        assertTrue(result);
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(evaluator);
    }

    @Test
    public void testSetInstance() throws Exception {
        Evaluator newEvaluator = mock(Evaluator.class);
        Evaluator.setInstance(newEvaluator);
        assertNotEquals(this.evaluator, Evaluator.getInstance());
        assertSame(newEvaluator, Evaluator.getInstance());
    }
}