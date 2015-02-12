package io.betterlife.framework.util;

import io.betterlife.framework.application.manager.FieldMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.util.EntityMockUtil;
import jersey.repackaged.com.google.common.collect.Maps;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class MockBaseObject extends BaseObject {
}

public class EntityUtilsTest {

    private EntityUtils entityUtils = EntityUtils.getInstance();
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(entityUtils);
        assertEquals(EntityUtils.class, entityUtils.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void testMapToBaseObjectNullObj() throws Exception {
        Map<String, Object> map = new HashMap<>();
        entityUtils.mapToBaseObject(null, map);
    }


    @Test
    public void testMapToBaseObject() throws Exception {
        MockBaseObject baseObject = new MockBaseObject();
        FieldMeta idMeta = EntityMockUtil.getInstance().mockFieldMeta("id", Integer.class);
        FieldMeta nameMeta = EntityMockUtil.getInstance().mockFieldMeta("name", String.class);
        MetaDataManager manager = Mockito.mock(MetaDataManager.class);
        when(manager.getFieldMeta(MockBaseObject.class, "id")).thenReturn(idMeta);
        when(manager.getFieldMeta(MockBaseObject.class, "name")).thenReturn(nameMeta);
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "name value");
        baseObject.setMetaDataManager(manager);
        entityUtils.mapToBaseObject(baseObject, map);
        assertEquals(1, baseObject.getValue("id"));
        assertEquals("name value", baseObject.getValue("name"));
    }

    @Test
    public void testIsBaseObjectNullClass() throws Exception {
        assertFalse(entityUtils.isBaseObject(null));
    }

    @Test
    public void testIsBaseObjectUsingBaseObject() {
        BaseObject bo = new BaseObject();
        assertTrue(entityUtils.isBaseObject(bo.getClass()));
    }

    @Test
    public void testIsBaseObjectUsingChild() {
        BaseObject bo = new MockBaseObject();
        assertTrue(entityUtils.isBaseObject(bo.getClass()));
    }

    @Test
    public void testIsBaseObjectUsingOtherClass() {
        assertFalse(entityUtils.isBaseObject(String.class));
        assertFalse(entityUtils.isBaseObject(Boolean.class));
        assertFalse(entityUtils.isBaseObject(EntityUtilsTest.class));
    }

    @Test
    public void testGetRepresentFieldWithDotNormal() throws Exception {
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("user", MockBaseObject.class);
        when(meta.getRepresentField()).thenReturn("id");
        assertEquals("user.id", entityUtils.getRepresentFieldWithDot(meta));
    }

    @Test
    public void testGetRepresentFieldWithDotNotBaseObject() throws Exception {
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("name", String.class);
        when(meta.getRepresentField()).thenReturn("name");
        assertEquals("name", entityUtils.getRepresentFieldWithDot(meta));
    }

    @Test(expected = RuntimeException.class)
    public void testGetRepresentFieldWithDotNull() throws Exception {
        entityUtils.getRepresentFieldWithDot(null);
    }

    @Test
    public void testIsIdField() throws Exception {
        assertTrue(entityUtils.isIdField("id"));
        assertFalse(entityUtils.isIdField("Id"));
        assertFalse(entityUtils.isIdField("ID"));
        assertFalse(entityUtils.isIdField("id "));
        assertFalse(entityUtils.isIdField(" id"));
        assertFalse(entityUtils.isIdField("xxxxx"));
        assertFalse(entityUtils.isIdField(""));
        assertFalse(entityUtils.isIdField(" "));
    }

    @Test
    public void testSortEntityMetaByDisplayRankChange() throws Exception {
        String[] names = new String[]{"id", "name", "amount", "quantity"};
        FieldMeta[] fieldMetas = new FieldMeta[]{
            EntityMockUtil.getInstance().mockFieldMetaWithDisplayRank("id", Integer.class, 5),
            EntityMockUtil.getInstance().mockFieldMetaWithDisplayRank("name", String.class, 0),
            EntityMockUtil.getInstance().mockFieldMetaWithDisplayRank("amount", BigDecimal.class, 1),
            EntityMockUtil.getInstance().mockFieldMetaWithDisplayRank("quantity", Integer.class, 1)
        };
        Map<String, FieldMeta> metaMap = new HashMap<>();
        for (int i = 0; i < fieldMetas.length; i++) {
            metaMap.put(names[i], fieldMetas[i]);
        }
        LinkedHashMap<String, FieldMeta> sortedMeta = entityUtils.sortEntityMetaByDisplayRank(metaMap);
        final int size = 4;
        assertEquals(size, sortedMeta.size());
        Iterator<String> iterator = sortedMeta.keySet().iterator();
        String[] keys = new String[size];
        for (int i = 0; i < size; i++) {
            keys[i] = iterator.next();
        }
        final String[] expectKeySequence = {"name", "amount", "quantity", "id"};
        assertArrayEquals(expectKeySequence, keys);
        for (int i = 0; i < expectKeySequence.length; i++) {
            assertEquals(metaMap.get(expectKeySequence[i]), sortedMeta.get(keys[i]));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testIsBooleanField() throws Exception {
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("active", Boolean.class);
        assertTrue(entityUtils.isBooleanField(meta));
        meta = EntityMockUtil.getInstance().mockFieldMeta("Incoming", String.class);
        assertFalse(entityUtils.isBooleanField(meta));
        entityUtils.isBooleanField(null);
    }
}