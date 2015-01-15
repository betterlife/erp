package io.betterlife.util.security;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Level;

/**
 * Author: Lawrence Liu
 * Date: 12/5/14
 */
public class LoginUtil {
    private static LoginUtil instance = new LoginUtil();
    private static final Logger logger = LogManager.getLogger(LoginUtil.class.getName());

    public static LoginUtil getInstance() {
        return instance;
    }

    private LoginUtil() {
    }

    public String cryptWithMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = str.getBytes();
              md.reset();
              byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (byte aDigested : digested) {
                sb.append(Integer.toHexString(0xff & aDigested));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Failed to get MD5 crypt algorithm", ex);
        }
        return null;
    }

    public String login(Map<String, Object> params) {
        User user;
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String encryptedPassword = cryptWithMD5(password).toUpperCase();
        try {
            if (logger.isDebugEnabled()) {
                logger.trace(String.format("Login request, [%s:%s(encrypted)]",username, encryptedPassword));
            }
            params.put("password", encryptedPassword);
            user = BaseOperator.getInstance().getBaseObject(User.GetByUserNameAndPasswordQuery, params);
        } catch (Exception e) {
            logger.error(String.format("Exception during login of user[%s], password[%s](encrypted)", username, encryptedPassword), e);
            logger.warn(String.format("Error to get user username[%s], password[%s](encrypted)", username, encryptedPassword));
            final ExecuteResult<String> result = new ExecuteResult<>();
            result.getErrorMessages().add("Failed to Login user [" + username + "]");
            return result.getRestString("");
        }
        return new ExecuteResult<BaseObject>().getRestString(user);
    }
}