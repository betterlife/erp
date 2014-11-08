package io.betterlife.rest.security;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.openjpa.persistence.meta.CompileTimeLogger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 * Security service, for login, logout etc.
 */
@Path("/")
@Stateless
public class LoginService {

    @PersistenceContext(unitName = ApplicationConfig.PersistenceUnitName)
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(LoginService.class.getName());

    @POST
    @Path("/login/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@PathParam("username") String username,
                        @PathParam("password") String password) throws IOException {
        User user = null;
        try {
            Map<String, String> params = new HashMap<>(2);
            params.put("username", username);
            params.put("password", password);
            user = BaseOperator.getInstance().getBaseObject(entityManager, User.GetByUserNameAndPasswordQuery, params);
        } catch (Exception e) {
            logger.log(
                Level.WARNING, String.format(
                    "Error to get user username[%s%], password[%s%]",
                    username, "HIDDEN_FOR_SECURITY"
                )
            );
        }
        return ExecuteResult.getRestString(user);
    }
}