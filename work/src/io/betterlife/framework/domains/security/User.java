package io.betterlife.framework.domains.security;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.condition.PasswordVisibleCondition;
import io.betterlife.framework.converter.PasswordConverter;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */

@Entity(name="UserEntity")
@NamedQueries({
    @NamedQuery(name = "User.getById", query = "SELECT u FROM UserEntity u WHERE u.id = :id AND u.active = TRUE"),
    @NamedQuery(name = "User.getAll",  query = "SELECT u FROM UserEntity u WHERE u.active = TRUE"),
    @NamedQuery(name = User.GetByUserNameAndPasswordQuery, query = "SELECT u from UserEntity u WHERE u.username = :username AND u.password = :password AND u.active = TRUE"),
    @NamedQuery(name = "User.getByKeyword", query = "SELECT u from UserEntity u WHERE (u.displayName like :keyword or u.username like :keyword) AND u.active = TRUE")
})
public class User extends BaseObject {

    public static final String GetByUserNameAndPasswordQuery = "User.getByUsernameAndPassword";

    public void setUsername(String username) {
        setValue("username",username);
    }

    @FormField(DisplayRank = 5)
    public String getUsername() {
        return getValue("username");
    }

    public void setPassword(String password) {
        setValue("password", password);
    }

    @FormField(DisplayRank = 15, Converter = PasswordConverter.class, Visible = PasswordVisibleCondition.class)
    public String getPassword() {
        return getValue("password");
    }

    @FormField(DisplayRank = 10)
    public String getDisplayName() {
        return getValue("displayName");
    }

    public void setDisplayName(String displayName) {
        setValue("displayName", displayName);
    }

    @FormField(DisplayRank = 15)
    public String getEmail() {
        return getValue("email");
    }

    public void setEmail(String email) {
        setValue("email", email);
    }
}