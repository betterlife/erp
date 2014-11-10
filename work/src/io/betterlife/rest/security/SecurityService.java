package io.betterlife.rest.security;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 * Security service, for login, logout etc.
 */
@Path("/security")
@Stateless
public class SecurityService {

    @PersistenceContext(unitName = ApplicationConfig.PersistenceUnitName)
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(SecurityService.class.getName());

    @POST
    @Path("/logout/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout(@PathParam("username") String username) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Logout user: " + username);
        }
        return ExecuteResult.getRestString("LOGOUT");
    }

    @POST
    @Path("/login/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@PathParam("username") String username,
                        @PathParam("password") String password) throws IOException {
        User user = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Login request, [%s:%s]", username, password));
            }
            Map<String, String> params = new HashMap<>(2);
            params.put("username", username);
            params.put("password", password);
            user = BaseOperator.getInstance().getBaseObject(entityManager, User.GetByUserNameAndPasswordQuery, params);
        } catch (Exception e) {
            logger.warn(String.format(
                            "Error to get user username[%s%], password[%s%]",
                            username, "HIDDEN_FOR_SECURITY"
                        )
            );
        }
        return ExecuteResult.getRestString(user);
    }
}