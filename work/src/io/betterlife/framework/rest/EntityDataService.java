package io.betterlife.framework.rest;

import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.erp.domains.financial.PaymentMethod;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.util.EntityUtils;
import io.betterlife.framework.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/2/14
 */
@Path("/")
public class EntityDataService {

    private static final Logger logger = LogManager.getLogger(EntityDataService.class.getName());

    @GET
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObjectByTypeAndId(@PathParam("id") long id,
                                       @PathParam("entityType") String entityType) throws IOException {
        final String idQueryForEntity = getNamedQueryRule().getIdQueryForEntity(entityType);
        final BaseObject entity = getBaseOperator().getBaseObjectById(id, idQueryForEntity);
        return new ExecuteResult<BaseObject>().getRestString(entity);
    }

    @GET
    @Path("/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllByObjectType(@PathParam("entityType") String entityType) throws IOException {
        List<BaseObject> result = getBaseOperator().getBaseObjects(
            getNamedQueryRule().getAllQueryForEntity(entityType),
            null);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Returning %s list %s", entityType, result));
        }
        final String resultStr = new ExecuteResult<List<BaseObject>>().getRestString(result);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Returning string: [%s]", resultStr));
        }
        return resultStr;
    }

    @GET
    @Path("/q/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String queryByObjectTypeAndKeyword(@PathParam("entityType") String entityType,
                                              @QueryParam("keyword") String keyword,
                                              @Context HttpServletRequest request,
                                              InputStream requestBody) throws IOException {
        Map<String, String> params = new HashMap<>(1);
        params.put("keyword", keyword);
        List<BaseObject> result = getObjectsByKeywords(entityType, params);
        return new ExecuteResult<List<BaseObject>>().getRestString(result);
    }

    private List<BaseObject> getObjectsByKeywords(String entityType, Map<String, String> parameters) {
        List<BaseObject> result = getBaseOperator().getBaseObjects(
            getNamedQueryRule().getKeywordQueryForEntity(entityType), parameters
        );
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Returning %s list %s", entityType, result));
        }
        return result;
    }

    @PUT
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("entityType") String entityType,
                         @PathParam("id") int id,
                         @Context HttpServletRequest request,
                         InputStream requestBody)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("betterlifeLoginUser");
        if (null == loginUser) {
            //Failure
            return new ExecuteResult<String>().getRestString("FAILED");
        }
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        Map<String, Object> entityParams = (Map<String, Object>) parameters.get("entity");
        BaseObject existingObj = getBaseOperator().getBaseObjectById(
            id, getNamedQueryRule().getIdQueryForEntity(entityType)
        );
        if (null != existingObj) {
            existingObj.setValues(entityParams);
            if (loginUser instanceof User) {
                existingObj.setValue("lastModify", loginUser);
            }
        }
        getBaseOperator().save(existingObj, BaseOperator.UPDATE_OPERA);
        return new ExecuteResult<String>().getRestString("SUCCESS");
    }

    @POST
    @Path("/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(@PathParam("entityType") String entityType,
                         @Context HttpServletRequest request,
                         InputStream requestBody)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        BaseObject obj = MetaDataManager.getInstance().entityObjectFromType(entityType);
        EntityUtils.getInstance().mapToBaseObject(obj, (Map<String, Object>) parameters.get("entity"));
        getBaseOperator().save(obj, BaseOperator.CREATE_OPERA);
        return new ExecuteResult<String>().getRestString("SUCCESS");
    }

    public NamedQueryRules getNamedQueryRule() {
        return NamedQueryRules.getInstance();
    }

    public BaseOperator getBaseOperator() {
        return BaseOperator.getInstance();
    }
}
