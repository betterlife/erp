package io.betterlife.framework.application;

import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.util.IOUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class I18nTest {

    private I18n i18n = I18n.getInstance();
    private ServletContext context;

    @Before
    public void setup() throws IOException {
        context = Mockito.mock(ServletContext.class);
        final String resourceName = "/WEB-INF/classes/resources/i18n/" + ApplicationConfig.getLocale() + ".csv";
        String input = "True,是\nid,编号\nUser,用户\nUser.displayName,显示名称\nuser,一般用户\nUser.user,操作人";
        InputStream stream = IOUtil.getInstance().stringToInputStream(input);
        when(context.getResourceAsStream(resourceName)).thenReturn(stream);
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(i18n);
    }

    @Test
    public void testSetInstance() throws Exception {
        final I18n mock = Mockito.mock(I18n.class);
        I18n.setInstance(mock);
        assertSame(mock, I18n.getInstance());
    }

    @Test
    public void testGetKeyExists() throws Exception {
        i18n.initResources(context);
        assertEquals("是", i18n.get("True", ApplicationConfig.getLocale()));
        assertEquals("编号", i18n.get("id", ApplicationConfig.getLocale()));
        assertEquals("用户", i18n.get("User", ApplicationConfig.getLocale()));
        assertEquals("一般用户", i18n.get("user", ApplicationConfig.getLocale()));
        assertEquals("操作人", i18n.get("User.user", ApplicationConfig.getLocale()));
        assertEquals("显示名称", i18n.get("User.displayName", ApplicationConfig.getLocale()));
    }

    @Test
    public void testGetKeyNotExists() throws IOException {
        i18n.initResources(context);
        assertEquals("Abc", i18n.get("abc", ApplicationConfig.getLocale()));
        assertEquals("Null", i18n.get(null, ApplicationConfig.getLocale()));
    }

    @Test
    public void testInitResourcesNormal() throws Exception {
        int count = i18n.initResources(context);
        assertEquals(6, count);
    }

    @Test
    public void testInitResourcesEmptyFile() throws Exception {
        context = Mockito.mock(ServletContext.class);
        final String resourceName = "/WEB-INF/classes/resources/i18n/" + ApplicationConfig.getLocale() + ".csv";
        String input = "";
        InputStream stream = IOUtil.getInstance().stringToInputStream(input);
        when(context.getResourceAsStream(resourceName)).thenReturn(stream);
        int count = i18n.initResources(context);
        assertEquals(0, count);
    }

    @Test
    public void testGetFieldLabel() throws Exception {
        i18n.initResources(context);
        String entityType = "User";
        assertEquals("编号", I18n.getInstance().getFieldLabel(entityType, "id"));
        assertEquals("一般用户", I18n.getInstance().getFieldLabel("Expense", "user"));
        assertEquals("操作人", I18n.getInstance().getFieldLabel(entityType, "user"));
        assertEquals("显示名称", I18n.getInstance().getFieldLabel(entityType, "displayName"));
        assertEquals("DisplayName1", I18n.getInstance().getFieldLabel(entityType, "displayName1"));
        assertEquals("Null", I18n.getInstance().getFieldLabel(entityType, null));
    }

}