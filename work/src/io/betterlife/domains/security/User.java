package io.betterlife.domains.security;

import io.betterlife.domains.BaseObject;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */

@Entity(name="UserEntity")
@NamedQueries({
    @NamedQuery(name = "User.getById", query = "SELECT u FROM UserEntity u WHERE u.id = :id AND u.active = TRUE"),
    @NamedQuery(name = "User.getAll",  query = "SELECT u FROM UserEntity u WHERE u.active = TRUE"),
    @NamedQuery(name = User.GetByUserNameAndPasswordQuery, query = "SELECT u from UserEntity u WHERE u.username = :username AND u.password = :password AND u.active = TRUE")
})
public class User extends BaseObject {

    public static final String GetByUserNameAndPasswordQuery = "User.getByUsernameAndPassword";

    public void setUsername(String username) {
        setValue("username",username);
    }

    public String getUsername() {
        return getValue("username");
    }

    public void setPassword(String password) {
        setValue("password", password);
    }

    public String getPassword() {
        return getValue("password");
    }

    public String getDisplayName() {
        return getValue("displayName");
    }

    public void setDisplayName(String displayName) {
        setValue("displayName", displayName);
    }
}