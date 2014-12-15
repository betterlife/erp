package io.betterlife.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TemplateUtilsTest {

    private TemplateUtils templateUtils;

    @Before
    public void setUp() throws Exception {
        templateUtils = TemplateUtils.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        TemplateUtils templateUtils = this.templateUtils;
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
            String result = templateUtils.getHtmlTemplate(context, absolutePath);
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
        String result = templateUtils.getHtmlTemplate(context, absolutePath);
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
            String result = templateUtils.getHtmlTemplate(context, absolutePath);
            assertEquals(data, result);
            verify(context, times(1)).getRealPath(absolutePath);
        } finally {
            FileUtils.forceDelete(new File(absolutePath));
        }
    }

    @Test
    public void testGetNgModelNameForField() throws Exception {
        String key = "user";
        assertEquals("entity.user", templateUtils.getNgModelNameForField(key));
    }

    @Test
    public void testGetFieldController() throws Exception {

    }

    @Test
    public void testGetBaseObjectController() throws Exception {

    }

    @Test
    public void testGetBooleanController() throws Exception {

    }

    @Test
    public void testGetIntegerController() throws Exception {

    }

    @Test
    public void testGetBigDecimalController() throws Exception {

    }

    @Test
    public void testGetDateController() throws Exception {

    }

    @Test
    public void testGetStringController() throws Exception {

    }

    @Test
    public void testGetButtonsController() throws Exception {

    }

    @Test
    public void testGetFieldLabelHtml() throws Exception {
        assertEquals("<label for='user' class='col-sm-2 control-label' id='user-label'>User</label>\n",
                     templateUtils.getFieldLabelHtml("user"));
        assertEquals("<label for='expense' class='col-sm-2 control-label' id='expense-label'>Expense</label>\n",
                     templateUtils.getFieldLabelHtml("expense"));
        assertEquals("<label for='expenseCategory' class='col-sm-2 control-label' id='expenseCategory-label'>ExpenseCategory</label>\n",
                     templateUtils.getFieldLabelHtml("expenseCategory"));
        assertEquals("<label for='' class='col-sm-2 control-label' id='-label'></label>\n",
                     templateUtils.getFieldLabelHtml(null));
        assertEquals("<label for='' class='col-sm-2 control-label' id='-label'></label>\n",
                     templateUtils.getFieldLabelHtml(""));
    }

    @Test
    public void testGetFieldLabel() throws Exception {
        assertEquals("User", templateUtils.getFieldLabel("user"));
        assertEquals("Expense", templateUtils.getFieldLabel("expense"));
        assertEquals("", templateUtils.getFieldLabel(""));
        assertEquals("", templateUtils.getFieldLabel(null));
    }

    @Test
    public void testGetListController() throws Exception {

    }
}