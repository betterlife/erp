package io.betterlife.io.betterlife.domains.security;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.rest.EntityService;
import io.betterlife.util.EntityMockUtil;
import io.betterlife.util.JsonUtils;
import io.betterlife.util.jpa.OpenJPAUtil;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.commons.io.IOUtils;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class EntityServiceTest {

    private BaseOperator operator;
    private EntityManager entityManager;
    private OpenJPAUtil openJPAUtil;
    private OpenJPAQuery openJPAQuery;
    private User existingUser = new User();
    private User newUser = new User();

    @Before
    public void setUp(){
        existingUser.setUsername("username");
        existingUser.setPassword("password");
        existingUser.setId(1);
        newUser.setUsername("username");
        newUser.setPassword("password");
        operator = mock(BaseOperator.class);
        openJPAUtil = mock(OpenJPAUtil.class);
        entityManager = mock(EntityManager.class);
        openJPAQuery = mock(OpenJPAQuery.class);
    }

    @Test
    public void testGetUser() throws IOException {
        when(operator.getBaseObjectById(entityManager, 1, "User.getById")).thenReturn(existingUser);
        when(openJPAUtil.getOpenJPAQuery(entityManager, "User.getById")).thenReturn(openJPAQuery);
        EntityService entityService = new EntityService();
        entityService.setEntityManager(entityManager);
        entityService.setOperator(operator);
        final String userFromDB = entityService.getObjectByTypeAndId(1, "User");
        final JsonNode node = JsonUtils.getInstance().stringToJsonNode(userFromDB);
        final String username = node.get("result").get("username").getTextValue();
        assertEquals("username", username);
        final String password = node.get("result").get("password").getTextValue();
        assertEquals("password", password);
        verify(operator, times(1)).getBaseObjectById(entityManager, 1, "User.getById");
    }

    @Test
    public void testCreateUser() throws ClassNotFoundException, IOException,
        InstantiationException, IllegalAccessException {
        EntityService entityService = new EntityService();
        entityService.setOperator(operator);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        EntityManager manager = EntityMockUtil.getInstance().mockObjectsForEntityService(entityService);
        Mockito.doNothing().when(manager).persist(newUser);
        Map<String, String> userMap = new HashMap<>(2);
        userMap.put("username", "xqliu");
        userMap.put("password", "password");
        InputStream stream = IOUtils.toInputStream(JsonUtils.getInstance().objectToJsonString(userMap));
        String response = entityService.create("user", servletRequest, stream);
        assertEquals(new ExecuteResult<String>().getRestString("SUCCESS"), response);
    }

    @Test
    public void testGetServiceEntity() throws IOException {
        EntityService service = new EntityService();
        EntityManager manager = EntityMockUtil.getInstance().mockObjectsForEntityService(service);
        String userMeta = service.getEntityMeta("User");
        JsonNode expect = JsonUtils.getInstance().stringToJsonNode("{\"result\":{\"id\":\"java.lang.Long\",\"username\":\"java.lang.String\",\"password\":\"java.lang.String\"},\"success\":true,\"successMessage\":null,\"errorMessages\":[]}\n");
        JsonNode node = JsonUtils.getInstance().stringToJsonNode(userMeta);
        assertEquals(expect, node);
    }


}
