package io.betterlife.util;

import io.betterlife.framework.domains.security.User;

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

    private EntityMockUtil(){}

    public static EntityMockUtil getInstance(){
        return instance;
    }

    public EntityManager mockEntityManagerAndMeta() {
        Set<Attribute> attributes = mockAttributes();
        ManagedType type = mockManagedType(attributes);
        Metamodel model = mockMetamodel(type);
        return mockEntityManager(model);
    }

    private EntityManager mockEntityManager(Metamodel model) {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.getMetamodel()).thenReturn(model);
        return entityManager;
    }

    private Metamodel mockMetamodel(ManagedType type) {
        Metamodel model = mock(Metamodel.class);
        Set<ManagedType<?>> types = new HashSet<>();
        types.add(type);
        when(model.getManagedTypes()).thenReturn(types);
        return model;
    }

    private ManagedType mockManagedType(Set<Attribute> attributes) {
        ManagedType type = mock(ManagedType.class);
        when(type.getJavaType()).thenReturn(User.class);
        when(type.getAttributes()).thenReturn(attributes);
        return type;
    }

    private Set<Attribute> mockAttributes() {
        Set<Attribute> attributes = new HashSet<>(4);
        attributes.add(mockAttribute(Long.class, "id"));
        attributes.add(mockAttribute(String.class, "username"));
        attributes.add(mockAttribute(String.class, "password"));
        return attributes;
    }

    private Attribute mockAttribute(Class javaType, String name) {
        Attribute attribute = mock(Attribute.class);
        when(attribute.getJavaType()).thenReturn(javaType);
        when(attribute.getName()).thenReturn(name);
        return attribute;
    }
}
