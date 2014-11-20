package io.betterlife.io.betterlife.application;

import io.betterlife.application.IndexFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
    public void setUp() throws ServletException, IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        FilterConfig filterConfig = mock(FilterConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(filterConfig.getServletContext()).thenReturn(context);
        when(filterConfig.getInitParameter("url-pattern"))
            .thenReturn("/dashboard,/user/*,/login/*,/logout/*");
        Mockito.doNothing().when(filterChain).doFilter(request, response);
        filter = new IndexFilter();
        filter.init(filterConfig);
    }

    @Test
    public void testDoFilterRoot() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");
        filter.doFilter(request, response, filterChain);
        verify(response,times(1)).sendRedirect("/#");
        verify(filterChain,times(1)).doFilter(request, response);
    }

    @Test
    public void testNoFilterRoot() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/#");
        filter.doFilter(request, response, filterChain);
        verify(response, times(0)).sendRedirect("/#");
        verify(filterChain,times(1)).doFilter(request, response);
    }

    @Test
    public void testIgnorePath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/js/jquery.js");
        filter.doFilter(request, response, filterChain);
        verify(response, times(0)).sendRedirect("/js/jquery.js");
        verify(filterChain,times(1)).doFilter(request, response);
    }

    @Test
    public void testRedirectPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/dashboard");
        filter.doFilter(request, response, filterChain);
        verify(response,times(1)).sendRedirect("/#dashboard");
        verify(filterChain,times(1)).doFilter(request, response);
    }

    @Test
    public void testNoRedirectPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/#dashboard");
        filter.doFilter(request, response, filterChain);
        verify(response,times(0)).sendRedirect("/#dashboard");
        verify(filterChain,times(1)).doFilter(request, response);
    }
}
