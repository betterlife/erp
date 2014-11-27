package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.application.ServiceEntityManager;
import io.betterlife.domains.BaseObject;
import io.betterlife.util.StringUtils;
import io.betterlife.util.TemplateUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/25/14
 */
@Path("/form")
@Stateless
public class EntityFormService {
    @PersistenceContext(unitName = ApplicationConfig.PersistenceUnitName)
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(EntityFormService.class.getName());

    private List<String> IgnoreFields;

    public List<String> getIgnoreFields() {
        if (null == IgnoreFields) {
            IgnoreFields = new ArrayList<>(8);
            IgnoreFields.add("id");
            IgnoreFields.add("lastModifyDate");
            IgnoreFields.add("lastModify");
            IgnoreFields.add("createDate");
            IgnoreFields.add("creator");
        }
        return IgnoreFields;
    }

    @GET @Path("/{entityType}/create")
    @Produces(MediaType.TEXT_HTML)
    public String getCreateForm(@PathParam("entityType") String entityType, @Context ServletContext context) {
        Map<String, Class> meta = ServiceEntityManager.getInstance().getMetaFromEntityType(entityManager, entityType);
        StringBuilder form = new StringBuilder();
        form.append("<div class='form-group form-horizontal'>");
        for (String key : meta.keySet()) {
            Class clazz = meta.get(key);
            if (getIgnoreFields().contains(key)) {
                continue;
            }
            form.append("<div class='form-group'>\n");
            final String label = appendLabel(form, key);
            appendFieldController(context, form, key, clazz, label);
            form.append("</div>");
        }
        appendButtons(entityType, form);
        form.append("</div>");
        form.append("<br/>");
        return form.toString();
    }

    private void appendButtons(String entityType, StringBuilder form) {
        form.append("<div class='form-group'>\n");
        form.append("<div class='col-sm-offset-1 col-sm-4'>");
        form.append("<input type='button' class='btn-default' value='Create ").append(StringUtils.capitalize(entityType)).append("' ng-click='create()'/>\n");
        form.append("<input type='button' class='btn-default' value='Reset ").append("' ng-click='reset()'/>\n");
        form.append("</div>");
        form.append("</div>");
    }

    private void appendFieldController(ServletContext context, StringBuilder form, String key, Class clazz, String label) {
        form.append("<div class='col-sm-4'>");
        if (String.class.equals(clazz)) {
            appendStringController(form, key, label);
        } else if (Date.class.equals(clazz)) {
            appendDateController(context, form, key);
        } else if (BigDecimal.class.equals(clazz)) {
            appendBigDecimalController(form, key);
        } else if (Integer.class.equals(clazz)) {
            appendIntegerController(form, key);
        } else if (Boolean.class.equals(clazz)) {
            appendBooleanController(form, key);
        } else if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
            appendBaseObjectController(form, key, clazz);
        }
        form.append("</div>");
    }

    private void appendBaseObjectController(StringBuilder form, String key, Class clazz) {
        form.append(key).append(" is a base object of type ").append(clazz);
    }

    private void appendBooleanController(StringBuilder form, String key) {
        form.append(key).append(" is a boolean");
    }

    private void appendIntegerController(StringBuilder form, String key) {
        form.append(key).append(" is a Integer");
    }

    private void appendBigDecimalController(StringBuilder form, String key) {
        form.append(key).append(" is a BigDecimal");
    }

    private void appendDateController(ServletContext context, StringBuilder form, String key) {
        String template = TemplateUtils.getInstance().getHtmlTemplate(context, "templates/date.tpl.html");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        form.append(template.replaceAll("\\$ngModel\\$", "entity." + key).replaceAll("\\$defaultValue\\$", sdf.format(new Date())));
    }

    private void appendStringController(StringBuilder form, String key, String label) {
        String type = "text";
        if ("password".equalsIgnoreCase(key)) {
            type = "password";
        }
        form.append(String.format("<input type='%s' class='form-control' ng-model='%s' name='%s' placeholder='%s' size='20'/>",type, key, key, label));
    }

    private String appendLabel(StringBuilder form, String key) {
        final String label = StringUtils.capitalize(key);
        form.append(String.format("<label for='%s' class='col-sm-1 control-label'>%s</label>\n", label, label));
        return label;
    }

    @GET @Path("/{entityName}/edit")
    @Produces(MediaType.TEXT_HTML)
    public String getEditForm(@PathParam("entityName") String entityName) {
        return "Edit Form";
    }

    @GET @Path("/{entityName}/list")
    @Produces(MediaType.TEXT_HTML)
    public String getListForm(@PathParam("entityName") String entityName) {
        return "List Form";
    }

    @GET @Path("/{entityName}/detail")
    @Produces(MediaType.TEXT_HTML)
    public String getDetailForm(@PathParam("entityName") String entityName) {
        return "Detail Form";
    }

}
