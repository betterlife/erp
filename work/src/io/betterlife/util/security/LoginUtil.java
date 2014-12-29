package io.betterlife.util.security;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

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

    public String login(Map<String, Object> params, String username, String password) {
        User user;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Login request, [%s:%s]",username, password));
            }
            user = BaseOperator.getInstance().getBaseObject(User.GetByUserNameAndPasswordQuery, params);
        } catch (Exception e) {
            logger.error(String.format("Exception during login of user[%s], password[%s]", username, password), e);
            logger.warn(String.format("Error to get user username[%s], password[%s]", username, password));
            final ExecuteResult<String> result = new ExecuteResult<>();
            result.getErrorMessages().add("Failed to Login user [" + username + "]");
            return result.getRestString("");
        }
        return new ExecuteResult<BaseObject>().getRestString(user);
    }
}