package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.application.ServiceEntityManager;
import io.betterlife.util.TemplateUtils;
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
import java.util.ArrayList;
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
    private TemplateUtils templateUtils;

    public TemplateUtils getTemplateUtils() {
        if (null == templateUtils) {
            templateUtils = TemplateUtils.getInstance();
        }
        return templateUtils;
    }

    public void setTemplateUtils(TemplateUtils templateUtils) {
        this.templateUtils = templateUtils;
    }

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
        for (Map.Entry<String, Class> entry : meta.entrySet()) {
            final Class clazz = entry.getValue();
            final String key = entry.getKey();
            if (getIgnoreFields().contains(key)) {
                continue;
            }
            form.append("<div class='form-group'>\n");
            form.append(getTemplateUtils().getFieldLabelHtml(key));
            form.append(getTemplateUtils().getFieldController(
                            context, entityManager, key,
                            clazz, getTemplateUtils().getFieldLabel(key)
                        ));
            form.append("</div>");
        }
        form.append(getTemplateUtils().getButtonsController(context, entityType));
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
