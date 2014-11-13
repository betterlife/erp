package io.betterlife.io.betterlife.domains.security;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.rest.EntityService;
import io.betterlife.util.jpa.OpenJPAUtil;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.io.IOException;

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

    @Test
    public void testGetUser() throws IOException {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setId(1);
        operator = mock(BaseOperator.class);
        openJPAUtil = mock(OpenJPAUtil.class);
        entityManager = mock(EntityManager.class);
        openJPAQuery = mock(OpenJPAQuery.class);
        when(operator.getBaseObjectById(entityManager, openJPAUtil, 1, "User.getById")).thenReturn(user);
        when(openJPAUtil.getOpenJPAQuery(entityManager, "User.getById")).thenReturn(openJPAQuery);
        EntityService entityService = new EntityService();
        entityService.setEntityManager(entityManager);
        entityService.setOpenJPAUtil(openJPAUtil);
        String result = ExecuteResult.getRestString(entityService.getObjectByTypeAndId(1, "User"));
        verify(operator).getBaseObjectById(entityManager, openJPAUtil, 1, "User.getById");
    }
}
