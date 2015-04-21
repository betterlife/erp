package io.betterlife.framework.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.persistence.BaseOperator;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginUtilTest {

    private static final String PASSWORD = "123qweasd";
    private static final String USER_NAME = "admin";
    LoginUtil loginUtil = LoginUtil.getInstance();
    private String ENCRYPTED_PASSWORD = "57ba172a6be125cca2f449826f9980ca".toUpperCase();

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(loginUtil);
    }

    @Test
    public void testLogin() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("username", USER_NAME);
        params.put("password", PASSWORD);
        mockBaseOperator(params);
        mockBLStringUtils();
        String result = loginUtil.login(request, params);
        JsonNode node = JsonUtils.getInstance().stringToJsonNode(result);
        assertEquals(ENCRYPTED_PASSWORD, node.get("result").get("password").asText());
        assertEquals(USER_NAME, node.get("result").get("username").asText());
        assertEquals(true, node.get("success").asBoolean());
        final JsonNode errorMessages = node.get("errorMessages");
        assertEquals(new ArrayNode(null), errorMessages);
    }

    @Test
    public void testLoginExceptionError() throws IOException {
        loginErrorInternal(new IOC() {
            @Override
            public Object action(Object... params) {
                when(((BaseOperator) params[0])
                         .getBaseObject(User.GetByUserNameAndPasswordQuery, (Map<String, Object>) params[1]))
                    .thenThrow(Exception.class);
                return null;
            }
        });
    }

    @Test
    public void testLoginNullError() throws IOException {
        loginErrorInternal(new IOC() {
            @Override
            public Object action(Object... params) {
                when(((BaseOperator) params[0])
                         .getBaseObject(User.GetByUserNameAndPasswordQuery, (Map<String, Object>) params[1]))
                    .thenReturn(null);
                return null;
            }
        });
    }

    protected void loginErrorInternal(IOC logic) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("username", USER_NAME);
        params.put("password", PASSWORD);
        BaseOperator operator = Mockito.mock(BaseOperator.class);
        logic.action(operator, params);
        BaseOperator.setInstance(operator);
        mockBLStringUtils();
        JsonNode node  = JsonUtils.getInstance().stringToJsonNode(loginUtil.login(request, params));
        assertNotNull(node);
        assertEquals(false, node.get("success").asBoolean());
        final ArrayNode errorMessages = (ArrayNode) node.get("errorMessages");
        assertNotEquals(new ArrayNode(null), errorMessages);
        assertEquals("Failed to Login user [" + USER_NAME + "]", errorMessages.get(0).asText());
    }

    private void mockBLStringUtils() {
        BLStringUtils bLStringUtils = Mockito.mock(BLStringUtils.class);
        when(bLStringUtils.cryptWithMD5(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        BLStringUtils.setInstance(bLStringUtils);
    }

    private void mockBaseOperator(Map<String, Object> params) {
        BaseOperator operator = Mockito.mock(BaseOperator.class);
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(ENCRYPTED_PASSWORD);
        when(operator.getBaseObject(User.GetByUserNameAndPasswordQuery, params)).thenReturn(user);
        BaseOperator.setInstance(operator);
    }
}