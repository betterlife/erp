package io.betterlife.util;

import io.betterlife.framework.application.manager.FieldMeta;
import io.betterlife.framework.domains.security.User;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class EntityMockUtil {

    private static EntityMockUtil instance = new EntityMockUtil();

    private EntityMockUtil() {
    }

    public static EntityMockUtil getInstance() {
        return instance;
    }

    public FieldMeta mockFieldMeta(final String name, final Class type) {
        FieldMeta meta = Mockito.mock(FieldMeta.class);
        when(meta.getName()).thenReturn(name);
        when(meta.getType()).thenReturn(type);
        return meta;
    }

    public FieldMeta mockFieldMetaWithDisplayRank(String id, Class clazz, int displayRank) {
        FieldMeta meta = mockFieldMeta(id, clazz);
        when(meta.getDisplayRank()).thenReturn(displayRank);
        return meta;
    }
}
