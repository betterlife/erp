package io.betterlife.application;

import io.betterlife.application.manager.SharedEntityManager;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Author: Lawrence Liu<br>
 * Date: 12/29/14<br>
 * This is a servlet context used to init the app.<br>
 * Currently what it does includes<br>
 * <ul>
 *     <li>Initialize Entity Manager</li>
 * </ul>
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SharedEntityManager.getInstance().initEntityManagerFactory();
        I18n.getInstance().initResources(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Do nothing now since all EntityManager should be closed separately.
    }
}
