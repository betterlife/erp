package io.betterlife.domains.security;

import io.betterlife.util.EntityVerifyUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;

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
        final String username1 = user.getUsername();
        assertNotNull(username1);
        assertSame(username, username1);
    }

    @Test
    public void testSetUsername() {
        final String username = "lxq";
        user.setUsername(username);
        final String newUsername = "newUserName";
        user.setUsername(newUsername);
        final String username1 = user.getUsername();
        assertNotEquals(username, username1);
        assertSame(newUsername, username1);
    }

    @Test
    public void testSetPassword() throws Exception {
        final String oldPassword = "passwOrd";
        user.setPassword(oldPassword);
        final String password = user.getPassword();
        assertNotNull(password);
        assertSame(oldPassword, password);
    }

    @Test
    public void testGetPassword() throws Exception {
        final String oldPwd = "passwOrd";
        user.setPassword(oldPwd);
        final String newPwd = "Passw0rd";
        user.setPassword(newPwd);
        final String password = user.getPassword();
        assertNotNull(password);
        assertNotEquals(oldPwd, password);
        assertSame(newPwd, password);
    }

    @Test
    public void testGetDisplayName() throws Exception {
        final String dn = "Liu Xiangqian";
        user.setDisplayName(dn);
        final String displayName = user.getDisplayName();
        assertNotNull(displayName);
        assertSame(dn, displayName);
    }

    @Test
    public void testSetDisplayName() throws Exception {
        final String dn = "Liu Xiangqian";
        user.setDisplayName(dn);
        final String nDn = "New Liu Xiangqian";
        user.setDisplayName(nDn);
        final String displayName = user.getDisplayName();
        assertNotNull(displayName);
        assertEquals(nDn, displayName);
        assertSame(nDn, displayName);
    }

    @Test
    public void testEntityAnnotationDefined() {
        Entity userEntity = User.class.getAnnotation(Entity.class);
        assertNotNull(userEntity);
        assertEquals("UserEntity", userEntity.name());
    }

    @Test
    public void testGetAllQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(User.class, User.class.getSimpleName() + "." + "getAll");
    }

    @Test
    public void testGetByIdQueryDefined() {
        EntityVerifyUtil.getInstance().verifyQueryDefined(User.class, User.class.getSimpleName() + "." + "getById");
    }

}