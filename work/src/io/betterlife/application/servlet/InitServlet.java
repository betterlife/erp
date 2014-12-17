package io.betterlife.application.servlet;

import io.betterlife.application.manager.SharedEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(value = "/hello", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @PersistenceUnit(unitName = "betterlife")
    EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        SharedEntityManager.getInstance().setFactory(this.emf);
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        EntityManager em = SharedEntityManager.getInstance().getEntityManager();
        if (em != null) {
            out.write("Your JPA configure is working!!! finally!".getBytes("UTF-8"));
        } else {
            out.write("Your JPA configuration is not working !!! still!".getBytes("UTF-8"));
        }
        out.flush();
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

}
