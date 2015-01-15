package io.betterlife.rest.security;

import io.betterlife.util.IOUtil;
import io.betterlife.util.rest.ExecuteResult;
import io.betterlife.util.security.LoginUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
         Map<String, Object> params = IOUtil.getInstance().inputStreamToJson(requestBody);
        return LoginUtil.getInstance().login(params);
    }
}