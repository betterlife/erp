package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.domains.BaseObject;
import io.betterlife.util.StringUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
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
    public String getCreateForm(@PathParam("entityType") String entityType) {
        Map<String, Class> meta = ServiceEntityManager.getInstance().getMetaFromEntityType(entityManager, entityType);
        StringBuilder form = new StringBuilder();
        form.append("<div class='form-group form-horizontal'>");
        for (String key : meta.keySet()) {
            Class clazz = meta.get(key);
            if (getIgnoreFields().contains(key)) {
                continue;
            }
            form.append("<div class='form-group'>\n");
            final String label = StringUtils.capitalize(key);
            form.append(String.format("<label for='%s' class='col-sm-1 control-label'>%s</label>\n", label, label));
            form.append("<div class='col-sm-4'>");
            if (clazz.equals(String.class)) {
                String type = "text";
                if ("password".equalsIgnoreCase(key)) {
                    type = "password";
                }
                form.append(String.format("<input type='%s' class='form-control' ng-model='%s' name='%s' placeholder='%s' size='20'/>",type, key, key, label));
            } else if (clazz.equals(Date.class)) {
                form.append(key).append(" is a date");
            } else if (clazz.equals(BigDecimal.class)) {
                form.append(key).append(" is a BigDecimal");
            } else if (clazz.equals(Integer.class)) {
                form.append(key).append(" is a Integer");
            } else if (clazz.equals(Boolean.class)) {
                form.append(key).append(" is a boolean");
            } else if (ClassUtils.getAllSuperclasses(clazz).contains(BaseObject.class)) {
                form.append(key).append(" is a base object of type ").append(clazz);
            }
            form.append("</div>");
            form.append("</div>");
        }
        form.append("<div class='form-group'>\n");
        form.append("<div class='col-sm-offset-1 col-sm-4'>");
        form.append("<input type='button' class='btn-default' value='Create ").append(StringUtils.capitalize(entityType)).append("' ng-click='create()'/>\n");
        form.append("</div>");
        form.append("</div>");
        form.append("</div>");
        form.append("<br/>");
        return form.toString();
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
