package io.betterlife.framework.util;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.condition.Evaluator;
import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/28/14
 */
public class TemplateUtils {
    private static TemplateUtils instance = new TemplateUtils();
    private static final Logger logger = LogManager.getLogger(TemplateUtils.class.getName());

    private Map<String, String> cachedTemplates = new HashMap<>(16);

    private TemplateUtils() {
    }

    public static TemplateUtils getInstance() {
        return instance;
    }

    public String getHtmlTemplate(ServletContext context, String filePath) {
        try {
            if (!filePath.startsWith("/")) {
                filePath = "/" + filePath;
            }
            if (null == cachedTemplates.get(filePath) || ApplicationConfig.getInstance().isDevelopmentMode()) {
                InputStream inputStream = context.getResourceAsStream(filePath);
                final String string = IOUtil.getInstance().inputStreamToString(inputStream);
                cachedTemplates.put(filePath, string);
            }
            return cachedTemplates.get(filePath);
        } catch (Exception e) {
            logger.warn(String.format("Failed to get resource for file[%s], Returning empty string", filePath));
            return StringUtils.EMPTY;
        }
    }

    @SuppressWarnings("unchecked")
    public String getFieldController(ServletContext context, String operationType,
                                     String entityType, FieldMeta fieldMeta, String label) {
        StringBuilder form = new StringBuilder();
        Class clazz = fieldMeta.getType();
        String key = fieldMeta.getName();
        form.append("<div class='form-group'>\n");
        form.append(getFieldLabelHtml(context, entityType, key));
        form.append("<div class='col-md-4'>");
        if (!Evaluator.getInstance().evalEditable(entityType, fieldMeta, null, operationType)) {
            form.append(getReadOnlyController(context, fieldMeta, clazz));
        }  else if (String.class.equals(clazz)) {
            form.append(getStringController(context, key, label));
        } else if (Date.class.equals(clazz)) {
            form.append(getDateController(context, key, new Date()));
        } else if (BigDecimal.class.equals(clazz)) {
            form.append(getNumberController(context, key, label));
        } else if (Integer.class.equals(clazz) || "int".equals(clazz.getSimpleName())) {
            form.append(getNumberController(context, key, label));
        } else if (Boolean.class.equals(clazz) || "boolean".equals(clazz.getSimpleName())) {
            form.append(getBooleanController(context, key));
        } else if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            form.append(getBaseObjectController(context, fieldMeta, clazz));
        } else if (Enum.class.isAssignableFrom(clazz)) {
            form.append(getEnumController(context, key, clazz));
        }
        form.append("</div>");
        form.append("</div>");
        return form.toString();
    }

    public String getReadOnlyController(ServletContext context, FieldMeta fieldMeta, Class clazz) {
        String ngModel = EntityUtils.getInstance().getNgModelNameForField(fieldMeta.getName());
        if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            ngModel += "." + fieldMeta.getRepresentField();
        }
        String template = getHtmlTemplate(context, "templates/readonly.tpl.html");
        return template.replaceAll("\\$ngModel", ngModel).replaceAll("\\$name", fieldMeta.getName());
    }

    public String getEnumController(ServletContext context, String key, Class clazz) {
        Object[] enumArray = clazz.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        for (Object enumVal : enumArray) {
            sb.append(
                String.format(
                    "<option value='%s'>%s</option>%n", enumVal.toString(),
                    I18n.getInstance().get(enumVal.toString(), ApplicationConfig.getInstance().getLocale())
                )
            );
        }
        String template = getHtmlTemplate(context, "templates/fields/enum.tpl.html");
        return template
            .replaceAll("\\$name", key)
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$options", sb.toString());
    }

    public String getBaseObjectController(ServletContext context, FieldMeta fieldMeta,
                                          Class<? extends BaseObject> clazz) {
        long number = BaseOperator.getInstance().getObjectCount(clazz);
        String result = "";
        if (number <= ApplicationConfig.getInstance().MaxNumberOfObjectForSelectController)
            result = getBaseObjectSelectController(context, fieldMeta, clazz);
        else {
            result = getBaseObjectTypeHeadController(context, fieldMeta, clazz);
        }
        return result;
    }

    public String getBaseObjectTypeHeadController(ServletContext context, FieldMeta fieldMeta,
                                                  Class<? extends BaseObject> clazz) {
        String result;
        String template = getHtmlTemplate(context, "templates/fields/baseobject.typehead.tpl.html");
        final String ngModelField = EntityUtils.getInstance().getNgModelNameForField(fieldMeta.getName());
        result = template
            .replaceAll("\\$name", fieldMeta.getName())
            .replaceAll("\\$ngModel", ngModelField)
            .replaceAll("\\$placeholder", I18n.getInstance().get(fieldMeta.getName(), ApplicationConfig.getInstance().getLocale()))
            .replaceAll("\\$entityType", BLStringUtils.uncapitalize(clazz.getSimpleName()))
            .replaceAll("\\$representField", fieldMeta.getRepresentField());
        return result;
    }

    public String getBaseObjectSelectController(ServletContext context, FieldMeta fieldMeta,
                                                Class<? extends BaseObject> clazz) {
        String result;
        final String simpleName = clazz.getSimpleName();
        //TODO.xqliu The list retrieved from DB was not updated according to latest DB.
        //TODO.xqliu Change to angularJS way, template does not owns list of data, it only owns display style of the data.
        //<!-- ng-options="item.id as item.description for item in getAllBaseObjects('$entityType', '$representField', '$name')" -->
        List<BaseObject> objects = BaseOperator.getInstance().getBaseObjects(
            NamedQueryRules.getInstance().getAllQueryForEntity(simpleName), null
        );
        StringBuilder sb = new StringBuilder();
        final String representField = fieldMeta.getRepresentField();
        final String methodName = "get" + BLStringUtils.capitalize(representField);
        final String fieldName = fieldMeta.getName();
        final String ngModelNameForField = EntityUtils.getInstance().getNgModelNameForField(fieldName);
        for (BaseObject baseObject : objects) {
            try {
                Object value = baseObject.getValue(representField);
                if (null == value) {
                    value = MethodUtils.invokeExactMethod(baseObject, methodName);
                }
                sb.append(String.format("%n\t<option value='%s'>%s</option>", baseObject.getId(), value));
            } catch (Exception e) {
                logger.error("Failed to get field[%s] of Object[%s] via method[%s]",
                             representField, baseObject, methodName);
            }
        }
        sb.append("\n");
        String template = getHtmlTemplate(context, "templates/fields/baseobject.select.tpl.html");
        result = template
            .replaceAll("\\$name", fieldName)
            .replaceAll("\\$ngModel", ngModelNameForField + ".id")
            .replaceAll("\\$options", sb.toString())
            .replaceAll("\\$representField", representField)
            .replaceAll("\\$entityType", BLStringUtils.uncapitalize(simpleName));
        return result;
    }

    public String getBooleanController(ServletContext context, String key) {
        String template = getHtmlTemplate(context, "templates/fields/boolean.tpl.html");
        return template
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$name", key);
    }

    public String getNumberController(ServletContext context, String key, String label) {
        String type = "number";
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$type", type)
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$name", key)
            .replaceAll("\\$placeholder", label);
    }

    public String getDateController(ServletContext context, String key, final Date defaultVal) {
        String template = getHtmlTemplate(context, "templates/fields/date.tpl.html");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return template
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$fieldKey", key)
            .replaceAll("\\$defaultValue", sdf.format(defaultVal));
    }

    public String getStringController(ServletContext context, String key, String label) {
        String type = "text";
        if ("password".equalsIgnoreCase(key)) {
            type = "password";
        }
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$type", type)
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$name", key)
            .replaceAll("\\$placeholder", label);
    }

    public String getEditButtons(ServletContext context, String entityType, final String operationType) {
        String template = getHtmlTemplate(context, "templates/fields/buttons.tpl.html");
        final String locale = ApplicationConfig.getInstance().getLocale();
        return template
            .replaceAll("\\$operationType", I18n.getInstance().get(operationType, locale))
            .replaceAll("\\$operation", BLStringUtils.uncapitalize(operationType))
            .replaceAll("\\$reset", I18n.getInstance().get("Reset", locale))
            .replaceAll("\\$entityType", I18n.getInstance().get(BLStringUtils.capitalize(entityType), locale));
    }

    public String getFieldLabelHtml(ServletContext context, String entityType, String key) {
        final String label = I18n.getInstance().getFieldLabel(entityType, key);
        String template = getHtmlTemplate(context, "templates/label.tpl.html");
        final String k = null == key ? BLStringUtils.EMPTY : key;
        return template.replaceAll("\\$key", k).replaceAll("\\$label", label);
    }

    public String getListController(ServletContext context, String entityType) {
        final String label = I18n.getInstance().get(BLStringUtils.capitalize(entityType),
                                                    ApplicationConfig.getInstance().getLocale());
        return getHtmlTemplate(context, "templates/list.tpl.html")
            .replaceAll("\\$entityType", BLStringUtils.uncapitalize(entityType))
            .replaceAll("\\$entityLabel", label);
    }

    public String getFormHtml(String entityType, ServletContext context, String operationType,
                              LinkedHashMap<String, FieldMeta> sortedMeta) {
        String frame = getFrameTemplate(context);
        String breadCrumb = getBreadCrumb(context, entityType, operationType);
        StringBuilder fields = new StringBuilder();
        for (Map.Entry<String, FieldMeta> entry : sortedMeta.entrySet()) {
            final FieldMeta fieldMeta = entry.getValue();
            final String key = entry.getKey();
            if (!Evaluator.getInstance().evalVisible(entityType, entry.getValue(), null, operationType)) continue;
            fields.append(getFieldController(context, operationType, entityType, fieldMeta,
                                             I18n.getInstance().getFieldLabel(entityType, key)));
        }
        String buttons = "";
        if (!Operation.DETAIL.equals(operationType)){
            buttons = getEditButtons(context, entityType, operationType);
        } else {
            buttons = getDetailButtons(context, entityType, operationType);
        }
        final String fieldsList = fields.toString();
        final String withBreadCrumb = frame.replace("$breadcrumb", breadCrumb);
        final String withButtons = withBreadCrumb.replace("$buttons", buttons);
        return withButtons.replace("$fieldsList", fieldsList);
    }

    private String getDetailButtons(ServletContext context, String entityType, String operationType) {
        return getHtmlTemplate(context, "templates/detail.buttons.tpl.html").replaceAll("\\$entityType", entityType);
    }

    public String getFrameTemplate(ServletContext context) {
        return getHtmlTemplate(context, "templates/form.tpl.html");
    }

    public String getBreadCrumb(ServletContext context, String entityType, String operationType) {
        final String locale = ApplicationConfig.getInstance().getLocale();
        final String label = I18n.getInstance().get(BLStringUtils.capitalize(entityType), locale);
        final String operationLabel = I18n.getInstance().get(BLStringUtils.capitalize(operationType), locale);
        return getHtmlTemplate(context, "templates/breadcrumb.tpl.html")
            .replaceAll("\\$entityType", BLStringUtils.uncapitalize(entityType))
            .replaceAll("\\$entityLabel", label)
            .replaceAll("\\$operationLabel", operationLabel);
    }
}
