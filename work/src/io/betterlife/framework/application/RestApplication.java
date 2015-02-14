package io.betterlife.framework.application;

import io.betterlife.framework.rest.EntityFormService;
import io.betterlife.framework.rest.EntityService;
import io.betterlife.framework.rest.SecurityService;

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
