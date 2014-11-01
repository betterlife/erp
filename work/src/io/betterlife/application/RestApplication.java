package io.betterlife.application;

import io.betterlife.rest.finical.ExpenseService;

import javax.naming.InitialContext;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 11/1/14
 */
public class RestApplication extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ExpenseService.class);
        return classes;
    }

    public Set<Object> getSingletons() {
         HashSet<Object> set = new HashSet<>();
         try {
             InitialContext ctx = new InitialContext();
             Object obj = ctx.lookup(Config.DataSourceName);
             set.add(obj);
         } catch (Exception ex) {
             throw new RuntimeException(ex);
         }
         return set;
      }

}
