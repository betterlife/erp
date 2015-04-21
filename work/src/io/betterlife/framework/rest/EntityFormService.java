package io.betterlife.framework.rest;

import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
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
        return getForm(entityType, context, Operation.CREATE);
    }

    @GET @Path("/{entityType}/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getEditForm(@PathParam("entityType") String entityType,
                              @Context HttpServletRequest request,
                              @PathParam("id") int id) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        return getForm(entityType, context, Operation.UPDATE);
    }

    public String getForm(String entityType, ServletContext context, final String operationType) {
        Map<String, FieldMeta> meta = MetaDataManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(meta);
        String formString = getTemplateUtils().getFormHtml(entityType, context, operationType, sortedMeta);
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

    @GET @Path("/{entityType}/detail/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getDetailForm(@PathParam("entityType") String entityType,
                                @Context HttpServletRequest request,
                                @PathParam("id") int id) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Map<String, FieldMeta> meta = MetaDataManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(meta);
        String formString = getTemplateUtils().getFormHtml(entityType, context, Operation.DETAIL, sortedMeta);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("%s form template for EntityType[%s]:%n\t%s", Operation.DETAIL, entityType, formString));
        }
        return formString;
    }

    @GET @Path("/{entityType}/relate/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getRelateForm(@PathParam("entityType") String entityType,
                                @Context HttpServletRequest request,
                                @PathParam("id") int id) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Map<String, FieldMeta> meta = MetaDataManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(meta);
        String formString = getTemplateUtils().getFormHtml(entityType, context, Operation.RELATE, sortedMeta);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("%s form template for EntityType[%s]:%n\t%s", Operation.RELATE, entityType, formString));
        }
        return formString;
    }

}
