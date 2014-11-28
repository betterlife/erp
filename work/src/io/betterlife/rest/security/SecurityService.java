package io.betterlife.rest.security;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.IOUtil;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
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
        return new ExecuteResult<String>().getRestString("LOGOUT");
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@Context HttpServletRequest request, InputStream requestBody) throws IOException {
         Map<String, String> params = IOUtil.getInstance().inputStreamToJson(requestBody);
        User user = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Login request, [%s:%s]",
                                           params.get("username"), params.get("password")));
            }
            user = BaseOperator.getInstance().getBaseObject(entityManager, User.GetByUserNameAndPasswordQuery, params);
        } catch (Exception e) {
            logger.warn(String.format(
                            "Error to get user username[%s], password[%s]",
                            params.get("username"), "HIDDEN_FOR_SECURITY"
                        )
            );
        }
        return new ExecuteResult<BaseObject>().getRestString(user);
    }


}