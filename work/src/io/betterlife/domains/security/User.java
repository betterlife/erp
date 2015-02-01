package io.betterlife.domains.security;

import io.betterlife.domains.BaseObject;
import io.betterlife.rest.Form;
import io.betterlife.util.converter.PasswordConverter;

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

    @Form(DisplayRank = 5)
    public String getUsername() {
        return getValue("username");
    }

    public void setPassword(String password) {
        setValue("password", password);
    }

    @Form(DisplayRank = 15, Converter = PasswordConverter.class)
    public String getPassword() {
        return getValue("password");
    }

    @Form(DisplayRank = 10)
    public String getDisplayName() {
        return getValue("displayName");
    }

    public void setDisplayName(String displayName) {
        setValue("displayName", displayName);
    }
}