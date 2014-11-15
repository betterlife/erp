package io.betterlife.io.betterlife.domains.security;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.rest.EntityService;
import io.betterlife.util.JsonUtils;
import io.betterlife.util.jpa.OpenJPAUtil;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class UserTest {

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
}
