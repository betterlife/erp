package io.betterlife.framework.condition;

import io.betterlife.framework.application.config.FormConfig;
import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.meta.FieldMeta;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommonFieldsVisibleConditionTest {

    @Test
    public void testEvaluate() throws Exception {
        FieldMeta fieldMeta = Mockito.mock(FieldMeta.class);
        when(fieldMeta.getName()).thenReturn("id");
        FormConfig formConfig = Mockito.mock(FormConfig.class);
        ArrayList<String> createIgnoreFields = new ArrayList<>();
        createIgnoreFields.add("id");
        when(formConfig.getCreateFormIgnoreFields()).thenReturn(createIgnoreFields);
        when(formConfig.getListFormIgnoreFields()).thenReturn(new ArrayList<String>());
        when(formConfig.getEditFormIgnoreFields()).thenReturn(new ArrayList<String>());
        FormConfig.setInstance(formConfig);
        CommonFieldsVisibleCondition condition = new CommonFieldsVisibleCondition();
        assertFalse(condition.evaluate(null, fieldMeta, null, Operation.CREATE));
        assertTrue(condition.evaluate(null, fieldMeta, null, Operation.UPDATE));
        assertTrue(condition.evaluate(null, fieldMeta, null, Operation.LIST));
        assertTrue(condition.evaluate(null, fieldMeta, null, Operation.DETAIL));
    }
}