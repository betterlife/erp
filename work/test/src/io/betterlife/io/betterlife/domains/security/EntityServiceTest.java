package io.betterlife.io.betterlife.domains.security;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.rest.EntityService;
import io.betterlife.util.JsonUtils;
import io.betterlife.util.jpa.OpenJPAUtil;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class EntityServiceTest {

    private BaseOperator operator;
    private EntityManager entityManager;
    private OpenJPAUtil openJPAUtil;
    private OpenJPAQuery openJPAQuery;
    private User user;

    @Before
    public void setUp(){
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setId(1);
        operator = mock(BaseOperator.class);
        openJPAUtil = mock(OpenJPAUtil.class);
        entityManager = mock(EntityManager.class);
        openJPAQuery = mock(OpenJPAQuery.class);
    }

    @Test
    public void testGetUser() throws IOException {
        when(operator.getBaseObjectById(entityManager, openJPAUtil, 1, "User.getById")).thenReturn(user);
        when(openJPAUtil.getOpenJPAQuery(entityManager, "User.getById")).thenReturn(openJPAQuery);
        EntityService entityService = new EntityService();
        entityService.setEntityManager(entityManager);
        entityService.setOpenJPAUtil(openJPAUtil);
        entityService.setOperator(operator);
        final String userFromDB = entityService.getObjectByTypeAndId(1, "User");
        final JsonNode node = JsonUtils.getInstance().stringToJsonNode(userFromDB);
        final String username = node.get("result").get("username").getTextValue();
        assertEquals("username", username);
        final String password = node.get("result").get("password").getTextValue();
        assertEquals("password", password);
        verify(operator).getBaseObjectById(entityManager, openJPAUtil, 1, "User.getById");
    }

    @Test
    public void testCreateUser() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        EntityService entityService = new EntityService();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        InputStream stream = mock(InputStream.class);
        entityService.create("User", servletRequest, stream);
        fail("Not Implemented");
    }

    @Test
    public void testGetServiceEntity() throws IOException {
        EntityService service = new EntityService();
        Set<Attribute> attributes = mockAttributes();
        ManagedType type = mockManagedType(attributes);
        Metamodel model = mockMetamodel(type);
        EntityManager manager = mockEntityManager(model);
        service.setEntityManager(manager);
        String userMeta = service.getEntityMeta("User");
        JsonNode expect = JsonUtils.getInstance().stringToJsonNode("{\"result\":{\"id\":\"java.lang.Long\",\"username\":\"java.lang.String\",\"password\":\"java.lang.String\"},\"success\":true,\"successMessage\":null,\"errorMessages\":[]}\n");
        JsonNode node = JsonUtils.getInstance().stringToJsonNode(userMeta);
        assertEquals(expect, node);
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
