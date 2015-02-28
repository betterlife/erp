package io.betterlife.framework.util;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.condition.Evaluator;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //if (null == cachedTemplates.get(filePath)) {
            InputStream inputStream = context.getResourceAsStream(filePath);
            final String string = IOUtil.getInstance().inputStreamToString(inputStream);
            cachedTemplates.put(filePath, string);
            //}
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
        form.append("<div class='col-md-4'>");
        if (!Evaluator.getInstance().evalEditable(entityType, fieldMeta, null, operationType)) {
            form.append(getReadOnlyController(fieldMeta, clazz));
        } else if (EntityUtils.getInstance().isIdField(key)) {
            form.append(getIdController(context, key, label));
        } else if (String.class.equals(clazz)) {
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
        return form.toString();
    }

    public String getReadOnlyController(FieldMeta fieldMeta, Class clazz) {
        String ngModel = EntityUtils.getInstance().getNgModelNameForField(fieldMeta.getName());
        if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            ngModel += "." + fieldMeta.getRepresentField();
        }
        return String.format("<input type='text' class='form-control' ng-model='%s' name='%s' size='20' disabled/>",
                             ngModel, fieldMeta.getName());
    }

    public String getIdController(ServletContext context, String key, String label) {
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$name", key)
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$placeholder", label)
            .replaceAll("\\$type", "hidden");
    }

    public String getEnumController(ServletContext context, String key, Class clazz) {
        Object[] enumArray = clazz.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        for (Object enumVal : enumArray) {
            sb.append(
                String.format(
                    "<option value='%s'>%s</option>%n", enumVal.toString(),
                    I18n.getInstance().get(enumVal.toString(), ApplicationConfig.getLocale())
                )
            );
        }
        String template = getHtmlTemplate(context, "templates/fields/enum.tpl.html");
        return template
            .replaceAll("\\$name", key)
            .replaceAll("\\$ngModel", EntityUtils.getInstance().getNgModelNameForField(key))
            .replaceAll("\\$options", sb.toString());
    }

    public String getBaseObjectController(ServletContext context, FieldMeta fieldMeta, Class<? extends BaseObject> clazz) {
        String template = getHtmlTemplate(context, "templates/fields/baseobject.tpl.html");
        final String ngModelField = EntityUtils.getInstance().getNgModelNameForField(fieldMeta.getName());
        return template
            .replaceAll("\\$name", fieldMeta.getName())
            .replaceAll("\\$ngModel", ngModelField)
            .replaceAll("\\$entityType", BLStringUtils.uncapitalize(clazz.getSimpleName()))
            .replaceAll("\\$representField", fieldMeta.getRepresentField());
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

    public String getButtonsController(ServletContext context, String entityType, final String operationType) {
        String template = getHtmlTemplate(context, "templates/fields/buttons.tpl.html");
        final String locale = ApplicationConfig.getLocale();
        return template
            .replaceAll("\\$operationType", I18n.getInstance().get(operationType, locale))
            .replaceAll("\\$operation", BLStringUtils.uncapitalize(operationType))
            .replaceAll("\\$reset", I18n.getInstance().get("Reset", locale))
            .replaceAll("\\$entityType", I18n.getInstance().get(BLStringUtils.capitalize(entityType), locale));
    }

    public String getFieldLabelHtml(String entityType, String key) {
        final String label = I18n.getInstance().getFieldLabel(entityType, key);
        return String.format(
            "<label for='%s' class='col-md-offset-2 col-md-2 control-label %s-label'>%s</label>%n",
            null == key ? BLStringUtils.EMPTY : key,
            null == key ? BLStringUtils.EMPTY : key, label
        );
    }

    public String getListController(ServletContext context) {
        return getHtmlTemplate(context, "templates/list.tpl.html");
    }

}
