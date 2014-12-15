package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.application.FormConfig;
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

    @GET @Path("/{entityType}/create")
    @Produces(MediaType.TEXT_HTML)
    public String getCreateForm(@PathParam("entityType") String entityType, @Context ServletContext context) {
        Map<String, Class> meta = ServiceEntityManager.getInstance().getMetaFromEntityType(entityManager, entityType);
        StringBuilder form = new StringBuilder();
        form.append("<div class='form-group form-horizontal'>");
        for (Map.Entry<String, Class> entry : meta.entrySet()) {
            final Class clazz = entry.getValue();
            final String key = entry.getKey();
            if (FormConfig.getInstance().getCreateFormIgnoreFields().contains(key)) {
                continue;
            }
            form.append("<div class='form-group'>\n");
            form.append(getTemplateUtils().getFieldLabelHtml(key));
            form.append(getTemplateUtils().getFieldController(
                            context, entityManager, entityType, key,
                            clazz, getTemplateUtils().getFieldLabel(key)
                        ));
            form.append("</div>");
        }
        form.append(getTemplateUtils().getButtonsController(context, entityType));
        form.append("</div>");
        form.append("<br/>");
        return form.toString();
    }

    @GET @Path("/{entityType}/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getEditForm(@PathParam("entityType") String entityType,
                              @Context ServletContext context,
                              @PathParam("id") int id) {
        return "Edit Form";
    }

    @GET @Path("/{entityType}/list")
    @Produces(MediaType.TEXT_HTML)
    public String getListForm(@PathParam("entityType") String entityType, @Context ServletContext context) {
        return getTemplateUtils().getListController(context, entityType);
    }

    @GET @Path("/{entityName}/detail")
    @Produces(MediaType.TEXT_HTML)
    public String getDetailForm(@PathParam("entityName") String entityName) {
        return "Detail Form";
    }

}
