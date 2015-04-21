package io.betterlife.framework.util;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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

    public String login(HttpServletRequest request, Map<String, Object> params) {
        User user;
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String encryptedPassword = BLStringUtils.getInstance().cryptWithMD5(password);
        try {
            if (logger.isDebugEnabled()) {
                logger.trace(String.format("Login request, [%s:%s(encrypted)]", username, encryptedPassword));
            }
            params.put("password", encryptedPassword);
            user = BaseOperator.getInstance().getBaseObject(User.GetByUserNameAndPasswordQuery, params);
            if (null == user) {
                request.getSession().removeAttribute("betterlifeLoginUser");
                return createErrorLoginResult(username, encryptedPassword);
            } else {
                request.getSession().setAttribute("betterlifeLoginUser", user.getUsername());
            }
        } catch (Exception e) {
            logger.error(String.format("Exception during login of user[%s], password[%s](encrypted)", username, encryptedPassword), e);
            return createErrorLoginResult(username, encryptedPassword);
        }
        return new ExecuteResult<BaseObject>().getRestString(user);
    }

    protected String createErrorLoginResult(String username, String encryptedPassword) {
        logger.warn(String.format("Error to get user username[%s], password[%s](encrypted)", username, encryptedPassword));
        final ExecuteResult<String> result = new ExecuteResult<>();
        result.getErrorMessages().add("Failed to Login user [" + username + "]");
        return result.getRestString("");
    }
}