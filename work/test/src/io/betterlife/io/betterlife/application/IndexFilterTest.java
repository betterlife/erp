package io.betterlife.io.betterlife.application;

import io.betterlife.application.IndexFilter;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class IndexFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private IndexFilter filter;

    @Before
    public void setUp() throws ServletException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        FilterConfig filterConfig = mock(FilterConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(filterConfig.getServletContext()).thenReturn(context);
        when(filterConfig.getInitParameter("url-pattern"))
            .thenReturn("/dashboard,/user/*,/login/*,/logout/*");
        filter = new IndexFilter();
        filter.init(filterConfig);
    }

    @Test
    public void testDoFilterRoot() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/#");
    }

    @Test
    public void testNoFilterRoot() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/#");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/#");
    }

    @Test
    public void testIgnorePath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/js/jquery.js");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/js/jquery.js");
    }

    @Test
    public void testRedirectPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/dashboard");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/#dashboard");
    }

    @Test
    public void testNoRedirectPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/#dashboard");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/#dashboard");
    }
}
