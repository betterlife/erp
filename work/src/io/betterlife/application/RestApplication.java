package io.betterlife.application;

import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.application.manager.SharedEntityManager;
import io.betterlife.rest.EntityFormService;
import io.betterlife.rest.EntityService;
import io.betterlife.rest.security.SecurityService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/1/14
 */
@ApplicationPath("rest")
public class RestApplication extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(EntityService.class);
        classes.add(SecurityService.class);
        classes.add(EntityFormService.class);
        return classes;
    }

    public Set<Object> getSingletons() {
        return new HashSet<>();
    }

}
