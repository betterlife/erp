package io.betterlife.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TemplateUtilsTest {

    @Test
    public void testGetInstance() throws Exception {
        TemplateUtils templateUtils = TemplateUtils.getInstance();
        assertNotNull(templateUtils);
    }

    @Test
    public void testGetHtmlTemplateEnglish() throws Exception {
        internalTestGetHtmlTemplate("I am English Content");
    }

    @Test
    public void testGetHtmlTemplateChinese() throws Exception {
        internalTestGetHtmlTemplate("我是中文~~~");
    }

    @Test
    public void testGetHtmlTemplateException() throws IOException {
        final String absolutePath = "/tmp/" + UUID.randomUUID() + ".tpl.html";
        final String encoding = "UTF-8";
        final String content = "我是中文~~~";
        try {
            IOUtil.getInstance().writeToAbsolutePath(absolutePath, content, encoding);
            ServletContext context = mock(ServletContext.class);
            when(context.getRealPath(absolutePath)).thenThrow(IOException.class);
            String result = TemplateUtils.getInstance().getHtmlTemplate(context, absolutePath);
            verify(context, times(1)).getRealPath(absolutePath);
            assertEquals(StringUtils.EMPTY, result);
        } finally {
            FileUtils.forceDelete(new File(absolutePath));
        }
    }

    @Test
    public void testGetHtmlTemplateFileNotFound() throws IOException {
        final String absolutePath = "/tmp/" + UUID.randomUUID() + ".tpl.html";
        ServletContext context = mock(ServletContext.class);
        when(context.getRealPath(absolutePath)).thenReturn(absolutePath);
        String result = TemplateUtils.getInstance().getHtmlTemplate(context, absolutePath);
        verify(context, times(1)).getRealPath(absolutePath);
        assertEquals(StringUtils.EMPTY, result);
    }

    private void internalTestGetHtmlTemplate(final String content) throws IOException {
        final String absolutePath = "/tmp/" + UUID.randomUUID() + ".tpl.html";
        final String encoding = "UTF-8";
        try {
            final String data = IOUtil.getInstance().writeToAbsolutePath(absolutePath, content, encoding);
            ServletContext context = mock(ServletContext.class);
            when(context.getRealPath(absolutePath)).thenReturn(absolutePath);
            String result = TemplateUtils.getInstance().getHtmlTemplate(context, absolutePath);
            assertEquals(data, result);
            verify(context, times(1)).getRealPath(absolutePath);
        } finally {
            FileUtils.forceDelete(new File(absolutePath));
        }
    }

    @Test
    public void testGetNgModelNameForField() throws Exception {
        String key = "user";
        assertEquals("entity.user", TemplateUtils.getInstance().getNgModelNameForField(key));
    }
}