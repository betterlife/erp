package io.betterlife.util;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.IOUtil;
import io.betterlife.framework.util.TemplateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TemplateUtilsTest {

    enum TestEnum {
        INCOMING, EXPENSE, OTHER;
    }

    private TemplateUtils templateUtils;

    @Before
    public void setUp() throws Exception {
        templateUtils = TemplateUtils.getInstance();
        I18n i18n = mock(I18n.class);
        when(i18n.getFieldLabel("Expense", "user")).thenReturn("用户");
        when(i18n.getFieldLabel("Expense", "expense")).thenReturn("Expense");
        when(i18n.getFieldLabel("Expense", "")).thenReturn("");
        when(i18n.getFieldLabel("Expense", "amount")).thenReturn("金额");
        when(i18n.getFieldLabel("Expense", "quantity")).thenReturn("数量");
        when(i18n.getFieldLabel("Expense", null)).thenReturn("");
        when(i18n.getFieldLabel("Expense", "expenseCategory")).thenReturn("支出分类");
        when(i18n.getFieldLabel("User", "password")).thenReturn("密码");
        when(i18n.getFieldLabel("PurchaseOrder", "id")).thenReturn("编号");
        when(i18n.get("Update", "zh_CN")).thenReturn("更新");
        when(i18n.get("Create", "zh_CN")).thenReturn("创建");
        when(i18n.get("Reset", "zh_CN")).thenReturn("重置");
        when(i18n.get("User", "zh_CN")).thenReturn("用户");
        when(i18n.get("Expense", "zh_CN")).thenReturn("支出");
        when(i18n.get("INCOMING", "zh_CN")).thenReturn("收入");
        when(i18n.get("EXPENSE", "zh_CN")).thenReturn("支出");
        when(i18n.get("OTHER", "zh_CN")).thenReturn("其他");
        I18n.setInstance(i18n);
    }

    @Test
    public void testGetInstance() throws Exception {
        TemplateUtils templateUtils = this.templateUtils;
        assertNotNull(templateUtils);
    }

    @Test
    public void testGetHtmlTemplateEnglish() throws Exception {
        internalTestGetHtmlTemplate("I am English Content", "/tmp/" + UUID.randomUUID() + ".tpl.html");
    }

    @Test
    public void testGetHtmlTemplateChinese() throws Exception {
        internalTestGetHtmlTemplate("我是中文~~~", "/tmp/" + UUID.randomUUID() + ".tpl.html");
    }

    @Test
    public void testGetHtmlTemplateNoSlashInPath() throws Exception {
        internalTestGetHtmlTemplate("我是中文~~~ttttt", "tmp/" + UUID.randomUUID() + ".tpl.html");
    }

    @Test
    public void testGetHtmlTemplateException() throws IOException {
        final String absolutePath = "/tmp/" + UUID.randomUUID() + ".tpl.html";
        try {
            IOUtil.getInstance().writeToAbsolutePath(absolutePath, "我是中文~~~", "UTF-8");
            internalVerifyEmptyResultUponException(absolutePath);
        } finally {
            FileUtils.forceDelete(new File(absolutePath));
        }
    }

    @Test
    public void testGetHtmlTemplateFileNotFound() throws IOException {
        internalVerifyEmptyResultUponException("/tmp/" + UUID.randomUUID() + ".tpl.html");
    }

    protected void internalVerifyEmptyResultUponException(String absolutePath) {
        ServletContext context = mock(ServletContext.class);
        when(context.getResourceAsStream(absolutePath)).thenThrow(IOException.class);
        String result = templateUtils.getHtmlTemplate(context, absolutePath);
        verify(context, times(1)).getResourceAsStream(absolutePath);
        assertEquals(StringUtils.EMPTY, result);
    }

    private void internalTestGetHtmlTemplate(final String content, final String absolutePath) throws IOException {
        final String encoding = "UTF-8";
        String updatedAbsolutePath = absolutePath;
        if (!absolutePath.startsWith("/")) {
            updatedAbsolutePath = "/" + absolutePath;
        }
        final File file = new File(updatedAbsolutePath);
        try {
            final String data = IOUtil.getInstance().writeToAbsolutePath(updatedAbsolutePath, content, encoding);
            final String fileContent = FileUtils.readFileToString(file);
            ServletContext context = mockServletContext(fileContent, updatedAbsolutePath);
            String result = templateUtils.getHtmlTemplate(context, absolutePath);
            assertEquals(data, result);
            verify(context, times(1)).getResourceAsStream(updatedAbsolutePath);
        } finally {
            FileUtils.forceDelete(file);
        }
    }

    @Test
    public void testGetReadOnlyControllerSimple() {
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("name", String.class);
        Class clazz = String.class;
        String expected = "<input type='text' class='form-control' ng-model='entity.name' name='name' size='20' disabled/>";
        assertEquals(expected, templateUtils.getReadOnlyController(meta, clazz));
    }

    class MockBaseObject extends BaseObject{}

    @Test
    public void testGetReadOnlyControllerBaseObject() {
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("object", MockBaseObject.class);
        when(meta.getRepresentField()).thenReturn("name");
        Class clazz = MockBaseObject.class;
        String expected = "<input type='text' class='form-control' ng-model='entity.object.name' name='object' size='20' disabled/>";
        assertEquals(expected, templateUtils.getReadOnlyController(meta, clazz));
    }

    @Test
    public void testGetEnumController() {
        String input = "<select name=\"$name\" class=\"form-control\" ng-model=\"$ngModel\">\n" +
            "$options\n" +
            "</select>";
        String key = "type";
        Class clazz = TestEnum.class;
        String expect = "<select name=\"" + key + "\" class=\"form-control\" ng-model=\"entity." + key + "\">\n" +
            "<option value='INCOMING'>收入</option>\n" +
            "<option value='EXPENSE'>支出</option>\n" +
            "<option value='OTHER'>其他</option>\n\n" +
            "</select>";
        ServletContext context = mockServletContext(input, "/templates/fields/enum.tpl.html");
        String result = templateUtils.getEnumController(context, key, clazz);
        assertEquals(expect, result);
    }

    @Test
    public void testGetFieldController() throws Exception {

    }

    @Test
    public void testGetBaseObjectSelectController() throws Exception {
        String input = "<select name=\"$name\" class=\"form-control\" ng-model=\"$ngModel\">$options</select>";
        String expect = "<select name=\"children\" class=\"form-control\" ng-model=\"entity.children.id\">\n\t<option value='13'>Object2-name</option>\n\t<option value='12'>Object1-name</option>\n</select>";
        ServletContext context = mockServletContext(input, "/templates/fields/baseobject.select.tpl.html");
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("children", BaseObject.class);
        when(meta.getRepresentField()).thenReturn("name");
        BaseOperator operator = Mockito.mock(BaseOperator.class);
        when(operator.getObjectCount(BaseObject.class)).thenReturn(2L);

        BaseObject object = Mockito.mock(BaseObject.class);
        when(object.getId()).thenReturn(12L);
        when(object.getActive()).thenReturn(false);
        when(object.getValue("name")).thenReturn("Object1-name");
        BaseObject object2 = Mockito.mock(BaseObject.class);
        when(object2.getId()).thenReturn(13L);
        when(object2.getValue("name")).thenReturn("Object2-name");
        when(object2.getActive()).thenReturn(true);
        List<BaseObject> allChild = new ArrayList<>(2);
        allChild.add(object2);
        allChild.add(object);

        when(operator.getBaseObjects("BaseObject.getAll", null)).thenReturn(allChild);
        BaseOperator.setInstance(operator);
        String result = templateUtils.getBaseObjectController(context, meta, BaseObject.class);
        assertEquals(expect, result);
    }

    @Test
    public void testGetBaseObjectTypeHeadController() throws Exception {
        String input = "<input type=\"hidden\" ng-model=\"$ngModel.id\" name=\"$name\">\n" +
            "<input type=\"text\" typeahead=\"baseObject.description for baseObject in getBaseObjects('$entityType', '$representField', $viewValue)\"\n" +
            "       typeahead-on-select=\"onTypeHeadSelect($item, $model, $label, '$name')\"\n" +
            "       ng-model=\"$ngModel.$representField\" typeahead-loading=\"loading$name\" class=\"form-control\">\n" +
            "<span ng-show=\"loading$name\" class=\"glyphicon glyphicon-refresh\"></span>";
        String expect = "<input type=\"hidden\" ng-model=\"entity.children.id\" name=\"children\">\n" +
            "<input type=\"text\" typeahead=\"baseObject.description for baseObject in getBaseObjects('baseObject', 'name', $viewValue)\"\n" +
            "       typeahead-on-select=\"onTypeHeadSelect($item, $model, $label, 'children')\"\n" +
            "       ng-model=\"entity.children.name\" typeahead-loading=\"loadingchildren\" class=\"form-control\">\n" +
            "<span ng-show=\"loadingchildren\" class=\"glyphicon glyphicon-refresh\"></span>";
        ServletContext context = mockServletContext(input, "/templates/fields/baseobject.typehead.tpl.html");
        FieldMeta meta = EntityMockUtil.getInstance().mockFieldMeta("children", BaseObject.class);
        when(meta.getRepresentField()).thenReturn("name");
        BaseOperator operator = Mockito.mock(BaseOperator.class);
        when(operator.getObjectCount(BaseObject.class)).thenReturn(10L);
        BaseObject object = Mockito.mock(BaseObject.class);
        when(object.getId()).thenReturn(12L);
        when(object.getActive()).thenReturn(false);
        when(object.getValue("name")).thenReturn("Object1-name");
        BaseObject object2 = Mockito.mock(BaseObject.class);
        when(object2.getId()).thenReturn(13L);
        when(object2.getValue("name")).thenReturn("Object2-name");
        when(object2.getActive()).thenReturn(true);
        List<BaseObject> allChild = new ArrayList<>(2);
        allChild.add(object2);
        allChild.add(object);

        when(operator.getBaseObjects("BaseObject.getAll", null)).thenReturn(allChild);
        BaseOperator.setInstance(operator);
        String result = templateUtils.getBaseObjectController(context, meta, BaseObject.class);
        assertEquals(expect, result);
    }

    @Test
    public void testGetBooleanController() throws Exception {
        String template = "<input name='$name' type=\"checkbox\" ng-model=\"$ngModel\" ng-true-value=\"true\" ng-false-value=\"false\" class=\"boolean-controller\">";
        String name = "active";
        String expect = "<input name='" + name + "' type=\"checkbox\" ng-model=\"entity." + name + "\" ng-true-value=\"true\" ng-false-value=\"false\" class=\"boolean-controller\">";
        ServletContext context = mockServletContext(template, "/templates/fields/boolean.tpl.html");
        String result = templateUtils.getBooleanController(context, name);
        assertEquals(expect, result);
    }

    @Test
    public void testGetDateController() throws Exception {
        String template =
            "<div class=\"row\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <p class=\"input-group\">\n" +
                "            <input type=\"text\" class=\"form-control\" datepicker-popup=\"yyyy-MM-dd\" is-open=\"calendar.opened.$fieldKey\"\n" +
                "                   ng-model=\"$ngModel\" ng-required=\"true\" close-text=\"Close\" init-date=\"$defaultValue\">\n" +
                "            <span class=\"input-group-btn\">\n" +
                "                <button type=\"button\" class=\"btn btn-default\" ng-click=\"calendar.open($event, '$fieldKey')\">\n" +
                "                    <i class=\"glyphicon glyphicon-calendar\"></i>\n" +
                "                </button>\n" +
                "            </span>\n" +
                "        </p>\n" +
                "    </div>\n" +
                "</div>\n";
        String fieldKey = "createDate";
        final Date defaultDate = new Date();
        String defaultDateStr = new SimpleDateFormat("yyyy-MM-dd").format(defaultDate);
        String expect =
            "<div class=\"row\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <p class=\"input-group\">\n" +
                "            <input type=\"text\" class=\"form-control\" datepicker-popup=\"yyyy-MM-dd\" is-open=\"calendar.opened." + fieldKey + "\"\n" +
                "                   ng-model=\"entity.createDate\" ng-required=\"true\" close-text=\"Close\" init-date=\"" + defaultDateStr + "\">\n" +
                "            <span class=\"input-group-btn\">\n" +
                "                <button type=\"button\" class=\"btn btn-default\" ng-click=\"calendar.open($event, '" + fieldKey + "')\">\n" +
                "                    <i class=\"glyphicon glyphicon-calendar\"></i>\n" +
                "                </button>\n" +
                "            </span>\n" +
                "        </p>\n" +
                "    </div>\n" +
                "</div>\n";
        ServletContext context = mockServletContext(template, "/templates/fields/date.tpl.html");
        String result = templateUtils.getDateController(context, fieldKey, defaultDate);
        assertEquals(expect, result);
    }


    @Test
    public void testGetIdController() {
        internalTestGetStringController("PurchaseOrder", "id", "hidden");
    }

    @Test
    public void testGetIntegerController() throws Exception {
        internalTestGetStringController("Expense", "amount", "number");
    }

    @Test
    public void testGetBigDecimalController() throws Exception {
        internalTestGetStringController("Expense", "quantity", "number");
    }

    @Test
    public void testGetStringController() throws Exception {
        internalTestGetStringController("Expense", "expenseCategory", "text");
    }

    @Test
    public void testGetPasswordStringController() throws Exception {
        internalTestGetStringController("User", "password", "password");
    }

    private void internalTestGetStringController(final String entity, final String fieldKey, final String type) {
        String template = "<input type='$type' class='form-control' ng-model='$ngModel' name='$name' placeholder='$placeholder' size='20'/>";
        String label = I18n.getInstance().getFieldLabel(entity, fieldKey);
        String expect = "<input type='" + type + "' class='form-control' ng-model='entity." + fieldKey + "' name='" + fieldKey + "' placeholder='" + label + "' size='20'/>";
        ServletContext context = mockServletContext(template, "/templates/fields/string.tpl.html");
        String result = "";
        if ("number".equals(type)) {
            result = templateUtils.getNumberController(context, fieldKey, label);
        } else if ("hidden".equals(type)) {
            result = templateUtils.getIdController(context, fieldKey, label);
        } else {
            result = templateUtils.getStringController(context, fieldKey, label);
        }
        assertEquals(expect, result);
    }

    @Test
    public void testGetButtonsControllerForCreate() {
        internalTestGetButtonController("Expense", "Create");
    }

    @Test
    public void testGetButtonsControllerForUpdate() throws Exception {
        internalTestGetButtonController("User", "Update");
    }

    protected void internalTestGetButtonController(final String entity, final String operation) {
        final String input = "<div class='form-group'>\n" +
            "    <div class='col-md-offset-4 col-md-4'>\n" +
            "        <input type='button' class='btn-default' value='$operationType$entityType' ng-click='$operation()'/>\n" +
            "        <input type='button' class='btn-default' value='$reset' ng-click='reset()'/>\n" +
            "    </div>\n" +
            "</div>";
        final String expect = "<div class='form-group'>\n" +
            "    <div class='col-md-offset-4 col-md-4'>\n" +
            "        <input type='button' class='btn-default' value='" + I18n.getInstance().get(operation, "zh_CN") + I18n.getInstance().get(entity, "zh_CN") + "' ng-click='" + BLStringUtils.uncapitalize(operation) + "()'/>\n" +
            "        <input type='button' class='btn-default' value='" + I18n.getInstance().get("Reset", "zh_CN") + "' ng-click='reset()'/>\n" +
            "    </div>\n" +
            "</div>";
        ServletContext context = mockServletContext(input, "/templates/fields/buttons.tpl.html");
        assertEquals(expect, templateUtils.getButtonsController(context, entity, operation));
    }

    @Test
    public void testGetFieldLabelHtml() throws Exception {
        String entityType = "Expense";
        assertEquals("<label for='user' class='col-md-offset-2 col-md-2 control-label user-label'>用户</label>\n",
                     templateUtils.getFieldLabelHtml(entityType, "user"));
        assertEquals("<label for='expense' class='col-md-offset-2 col-md-2 control-label expense-label'>Expense</label>\n",
                     templateUtils.getFieldLabelHtml(entityType, "expense"));
        assertEquals("<label for='expenseCategory' class='col-md-offset-2 col-md-2 control-label expenseCategory-label'>支出分类</label>\n",
                     templateUtils.getFieldLabelHtml(entityType, "expenseCategory"));
        assertEquals("<label for='' class='col-md-offset-2 col-md-2 control-label -label'></label>\n",
                     templateUtils.getFieldLabelHtml(entityType, null));
        assertEquals("<label for='' class='col-md-offset-2 col-md-2 control-label -label'></label>\n",
                     templateUtils.getFieldLabelHtml(entityType, ""));
    }

    @Test
    public void testGetListController() throws Exception {
        final String input = "<div id=\"grid1\" ui-grid=\"gridOptions\" ui-grid-pagination class=\"grid\" external-scopes=\"myViewModel\"></div>";
        ServletContext context = mockServletContext(input, "/templates/list.tpl.html");
        assertEquals(input, templateUtils.getListController(context, "user"));
    }

    private ServletContext mockServletContext(String input, final String absolutePath) {
        ServletContext context = mock(ServletContext.class);
        InputStream is = IOUtils.toInputStream(input);
        when(context.getResourceAsStream(absolutePath)).thenReturn(is);
        return context;
    }
}