package io.betterlife.util;

import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.NamedQueryRules;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/28/14
 */
public class TemplateUtils {
    private static TemplateUtils instance = new TemplateUtils();
    private static final Logger logger = LogManager.getLogger(TemplateUtils.class.getName());

    private TemplateUtils(){}

    public static TemplateUtils getInstance() {
        return instance;
    }

    public String getHtmlTemplate(ServletContext context, String filePath) {
        try {
            String realPath = context.getRealPath(filePath);
            return FileUtils.readFileToString(new File(realPath), "UTF-8");
        } catch (Exception e) {
            logger.warn(String.format("Failed to get resource for file[%s], Returning empty string", filePath));
            return StringUtils.EMPTY;
        }
    }

    @SuppressWarnings("unchecked")
    public String getFieldController(ServletContext context, EntityManager entityManager,
                                     String key, Class clazz, String label) {
        StringBuilder form = new StringBuilder();
        form.append("<div class='col-sm-4'>");
        if (String.class.equals(clazz)) {
            form.append(getStringController(context, key, label));
        } else if (Date.class.equals(clazz)) {
            form.append(getDateController(context, key));
        } else if (BigDecimal.class.equals(clazz)) {
            form.append(getBigDecimalController(context, key, label));
        } else if (Integer.class.equals(clazz)) {
            form.append(getIntegerController(key));
        } else if (Boolean.class.equals(clazz)) {
            form.append(getBooleanController(key));
        } else if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            form.append(getBaseObjectController(context, entityManager, key, clazz));
        }
        form.append("</div>");
        return form.toString();
    }

    public String getBaseObjectController(ServletContext context, EntityManager entityManager,
                                          String key, Class<? extends BaseObject> clazz) {
        List<BaseObject> objects = BaseOperator.getInstance().getBaseObjects(
            entityManager,
            NamedQueryRules.getInstance().getAllQueryForEntity(clazz.getSimpleName())
        );
        StringBuilder sb = new StringBuilder();
        for (BaseObject baseObject : objects) {
            sb.append(String.format("<option value='%s'>%s</option>\n", baseObject.getId(), baseObject.getId()));
        }
        String template = getHtmlTemplate(context, "templates/fields/baseobject.tpl.html");
        return template
            .replaceAll("\\$name", key)
            .replaceAll("\\$ngModel", getNgModelNameForField(key))
            .replaceAll("\\$options", sb.toString());
    }

    public String getBooleanController(String key) {
        return key + " is a boolean";
    }

    public String getIntegerController(String key) {
        return key + " is a Integer";
    }

    public String getBigDecimalController(ServletContext context, String key, String label) {
        String type="number";
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

    public String getButtonsController(ServletContext context, String entityType) {
        String template = getHtmlTemplate(context, "templates/fields/buttons.tpl.html");
        return template.replaceAll("\\$entityType", entityType);
    }

    public String getFieldLabelHtml(String key) {
        final String label = getFieldLabel(key);
        return String.format("<label for='%s' class='col-sm-2 control-label'>%s</label>%n",
                             null == key ? BLStringUtils.EMPTY : key, label);
    }

    public String getFieldLabel(String key) {
        if (null == key) {
            return BLStringUtils.EMPTY;
        }
        return BLStringUtils.capitalize(key);
    }

    public String getListController(ServletContext context, String entityType) {
        return getHtmlTemplate(context, "templates/list.tpl.html");
    }

    public String getNgModelNameForField(String key) {
        return "entity." + key;
    }

}
