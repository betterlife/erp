package io.betterlife.domains.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setup() {
        user = new User();
    }

    @Test
    public void testGetUsername() throws Exception {
        final String username = "lxq";
        user.setUsername(username);
        assertNotNull(user.getUsername());
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testSetUsername() {
        final String username = "lxq";
        user.setUsername(username);
        final String newUsername = "newUserName";
        user.setUsername(newUsername);
        assertEquals(newUsername, user.getUsername());
        assertNotEquals(username, user.getUsername());
    }

    @Test
    public void testSetPassword() throws Exception {
        final String oldPassword = "passwOrd";
        user.setPassword(oldPassword);
        assertNotNull(user.getPassword());
        assertEquals(oldPassword, user.getPassword());
    }

    @Test
    public void testGetPassword() throws Exception {
        final String oldPwd = "passwOrd";
        user.setPassword(oldPwd);
        final String newPwd = "Passw0rd";
        user.setPassword(newPwd);
        assertNotNull(user.getPassword());
        assertEquals(newPwd, user.getPassword());
        assertNotEquals(oldPwd, user.getPassword());
    }

    @Test
    public void testGetDisplayName() throws Exception {
        final String dn = "Liu Xiangqian";
        user.setDisplayName(dn);
        assertNotNull(user.getDisplayName());
        assertEquals(dn, user.getDisplayName());
    }

    @Test
    public void testSetDisplayName() throws Exception {
        final String dn = "Liu Xiangqian";
        user.setDisplayName(dn);
        final String nDn = "New Liu Xiangqian";
        user.setDisplayName(nDn);
        assertNotNull(user.getDisplayName());
        assertEquals(nDn, user.getDisplayName());
        assertNotEquals(dn, user.getDisplayName());
    }
}