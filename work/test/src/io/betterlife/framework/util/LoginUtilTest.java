package io.betterlife.framework.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginUtilTest {

    LoginUtil loginUtil = LoginUtil.getInstance();
    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(loginUtil);
    }

    @Test
    public void testCryptWithMD5() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }
}