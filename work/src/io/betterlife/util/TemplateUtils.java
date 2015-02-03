package io.betterlife.util;

import io.betterlife.application.I18n;
import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.application.manager.FieldMeta;
import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.NamedQueryRules;
import io.betterlife.util.condition.Evaluator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/28/14
 */
public class TemplateUtils {
    private static TemplateUtils instance = new TemplateUtils();
    private static final Logger logger = LogManager.getLogger(TemplateUtils.class.getName());

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
            logger.debug("filePath: " + filePath);
            InputStream inputStream = context.getResourceAsStream(filePath);
            logger.debug("inputStream: " + inputStream);
            final String string = IOUtil.getInstance().inputStreamToString(inputStream);
            logger.debug("Template String: " + string);
            return string;
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
        form.append("<div class='col-sm-4'>");
        if (!Evaluator.evalEditable(entityType, fieldMeta, null, operationType)) {
            form.append(getReadOnlyController(fieldMeta, clazz));
        } else if (EntityUtils.getInstance().isIdField(key)) {
            form.append(getIdController(context, key, label));
        } else if (String.class.equals(clazz)) {
            form.append(getStringController(context, key, label));
        } else if (Date.class.equals(clazz)) {
            form.append(getDateController(context, key));
        } else if (BigDecimal.class.equals(clazz)) {
            form.append(getBigDecimalController(context, key, label));
        } else if (Integer.class.equals(clazz) || "int".equals(clazz.getSimpleName())) {
            form.append(getIntegerController(context, key, label));
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

    private String getReadOnlyController(FieldMeta fieldMeta, Class clazz) {
        String ngModel = getNgModelNameForField(fieldMeta.getName());
        if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            ngModel += "." + fieldMeta.getRepresentField();
        }
        return String.format("<input type='text' class='form-control' ng-model='%s' name='%s' size='20' disabled/>", ngModel, fieldMeta);
    }

    private String getIdController(ServletContext context, String key, String label) {
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$name", key)
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$placeholder", label)
            .replaceAll("\\$type", "hidden");
    }

    private String getEnumController(ServletContext context, String key, Class clazz) {
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
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$options", sb.toString());
    }

    public String getBaseObjectController(ServletContext context, FieldMeta fieldMeta, Class<? extends BaseObject> clazz) {
        List<BaseObject> objects = BaseOperator.getInstance().getBaseObjects(
            NamedQueryRules.getInstance().getAllQueryForEntity(clazz.getSimpleName())
        );
        StringBuilder sb = new StringBuilder();
        for (BaseObject baseObject : objects) {
            sb.append(String.format("<option value='%s'>%s</option>%n", baseObject.getId(),
                                    baseObject.getValue(fieldMeta.getRepresentField())));
        }
        String template = getHtmlTemplate(context, "templates/fields/baseobject.tpl.html");
        return template
            .replaceAll("\\$name", fieldMeta.getName())
            .replaceAll("\\$ngModel", getNgModelNameForField(fieldMeta.getName()) + ".id")
            .replaceAll("\\$options", sb.toString());
    }

    public String getBooleanController(ServletContext context, String key) {
        String template = getHtmlTemplate(context, "templates/fields/boolean.tpl.html");
        return template
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$name", key);
    }

    public String getIntegerController(ServletContext context, String key, String label) {
        String type = "number";
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$type", type)
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$name", key)
            .replaceAll("\\$placeholder", label);
    }

    public String getBigDecimalController(ServletContext context, String key, String label) {
        String type = "number";
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$type", type)
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$name", key)
            .replaceAll("\\$placeholder", label);
    }

    public String getDateController(ServletContext context, String key) {
        String template = getHtmlTemplate(context, "templates/fields/date.tpl.html");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return template
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$fieldKey", key)
            .replaceAll("\\$defaultValue", sdf.format(new Date()));
    }

    public String getStringController(ServletContext context, String key, String label) {
        String type = "text";
        if ("password".equalsIgnoreCase(key)) {
            type = "password";
        }
        String template = getHtmlTemplate(context, "templates/fields/string.tpl.html");
        return template
            .replaceAll("\\$type", type)
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
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
        final String label = getFieldLabel(entityType, key);
        return String.format(
            "<label for='%s' class='col-sm-2 control-label %s-label'>%s</label>%n",
            null == key ? BLStringUtils.EMPTY : key,
            null == key ? BLStringUtils.EMPTY : key, label
        );
    }

    public String getFieldLabel(String entityType, String key) {
        return I18n.getInstance().getFieldLabel(entityType, key, ApplicationConfig.getLocale());
    }

    public String getListController(ServletContext context) {
        return getHtmlTemplate(context, "templates/list.tpl.html");
    }

    public String getNgModelNameForField(String key) {
        return "entity." + key;
    }

}
