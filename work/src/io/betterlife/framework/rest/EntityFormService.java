package io.betterlife.framework.rest;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.condition.Evaluator;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.EntityUtils;
import io.betterlife.framework.util.TemplateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/25/14
 */
@Path("/form")
public class EntityFormService {

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
    public String getCreateForm(@PathParam("entityType") String entityType, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        return getForm(entityType, context, "Create");
    }

    @GET @Path("/{entityType}/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getEditForm(@PathParam("entityType") String entityType,
                              @Context HttpServletRequest request,
                              @PathParam("id") int id) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        return getForm(entityType, context, "Update");
    }

    public String getForm(String entityType, ServletContext context, final String operationType) {
        Map<String, FieldMeta> meta = MetaDataManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(meta);
        final String locale = ApplicationConfig.getLocale();
        final String label = I18n.getInstance().get(BLStringUtils.capitalize(entityType), locale);
        final String operationLabel = I18n.getInstance().get(BLStringUtils.capitalize(operationType), locale);
        String formString = getTemplateUtils().getFormHtmlFromTemplate(entityType, context, operationType,
                                                                       sortedMeta, label, operationLabel);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("%s form template for EntityType[%s]:%n\t%s", operationType, entityType, formString));
        }
        return formString;
    }

    @GET @Path("/{entityType}/list")
    @Produces(MediaType.TEXT_HTML)
    public String getListForm(@PathParam("entityType") String entityType, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        final String formString = getTemplateUtils().getListController(context, entityType);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("List template for EntityType[%s]:%n\t%s", entityType, formString));
        }
        return formString;
    }

    @GET @Path("/{entityName}/detail")
    @Produces(MediaType.TEXT_HTML)
    public String getDetailForm(@PathParam("entityName") String entityName) {
        return "Detail Form";
    }

}
