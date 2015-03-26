package io.betterlife.framework.application;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author: Lawrence Liu
 * Date: 15/3/25
 */
public class AngularFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest) req).getRequestURI();
        if (uri.equals("/")
            || uri.equals("/build.txt")
            || uri.equals("/favicon.ico")
            || uri.startsWith("/rest")
            || uri.startsWith("/js")
            || uri.startsWith("/templates")
            || uri.startsWith("/css")) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
