package io.betterlife.application;

import io.betterlife.application.manager.SharedEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

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
    private final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SharedEntityManager.getInstance().initEntityManagerFactory();
        try {
            I18n.getInstance().initResources(servletContextEvent.getServletContext());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Do nothing now since all EntityManager should be closed separately.
    }
}
