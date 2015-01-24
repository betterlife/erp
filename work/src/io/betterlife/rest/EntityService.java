package io.betterlife.rest;

import io.betterlife.application.I18n;
import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.application.config.FormConfig;
import io.betterlife.application.manager.FieldMeta;
import io.betterlife.application.manager.ServiceEntityManager;
import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.NamedQueryRules;
import io.betterlife.util.EntityUtils;
import io.betterlife.util.IOUtil;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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
public class EntityService {

    private static final Logger logger = LogManager.getLogger(EntityService.class.getName());

    private NamedQueryRules namedQueryRule;
    private BaseOperator operator;

    @GET
    @Path("/entity/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEntityMeta(@PathParam("entityType") String entityType) throws IOException {
        if (logger.isTraceEnabled()) {
            logger.trace("Getting entity meta data for " + entityType);
        }
        Map<String, FieldMeta> meta = ServiceEntityManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(entityType, meta);
        List<Map<String, Object>> list = new ArrayList<>(sortedMeta.size());
        for (Map.Entry<String, FieldMeta> entry : sortedMeta.entrySet()) {
            if (FormConfig.getInstance().getListFormIgnoreFields().contains(entry.getKey())) {
                continue;
            }
            String field = entry.getKey();
            if (EntityUtils.getInstance().isBaseObject(entry.getValue().getType())) {
                field = EntityUtils.getInstance().getRepresentFieldWithDot(entityType, field);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("field", field);
            if (Objects.equals(field, "amount")) {
                map.put("aggregationType", 2);
            }
            map.put("name", I18n.getInstance().getFieldLabel(entityType, entry.getKey(), ApplicationConfig.getLocale()));
            list.add(map);
        }
        String result = new ExecuteResult<List<Map<String, Object>>>().getRestString(list);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entity[%s]'s meta: %n\t%s", entityType, result));
        }
        return result;
    }


    @GET
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObjectByTypeAndId(@PathParam("id") long id,
                                       @PathParam("entityType") String entityType) throws IOException {
        final String idQueryForEntity = getNamedQueryRule().getIdQueryForEntity(entityType);
        final BaseObject entity = getOperator().getBaseObjectById(id, idQueryForEntity);
        return new ExecuteResult<BaseObject>().getRestString(entity);
    }

    @GET
    @Path("/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllByObjectType(@PathParam("entityType") String entityType) throws IOException {
        List<BaseObject> result = getOperator().getBaseObjects(
            getNamedQueryRule().getAllQueryForEntity(entityType)
        );
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Returning %s list %s", entityType, result));
        }
        return new ExecuteResult<List<BaseObject>>().getRestString(result);
    }

    @PUT
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("entityType") String entityType,
                         @PathParam("id") int id,
                         @Context HttpServletRequest request,
                         InputStream requestBody)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        Map<String, Object> entityParams = (Map<String, Object>) parameters.get("entity");
        BaseObject existingObj = getOperator().getBaseObjectById(
            id, getNamedQueryRule().getIdQueryForEntity(entityType)
        );
        if (null != existingObj) {
            existingObj.setValues(entityParams);
        }
        getOperator().save(existingObj, BaseOperator.UPDATE_OPERA);
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
        BaseObject obj = ServiceEntityManager.getInstance().entityObjectFromType(entityType);
        EntityUtils.getInstance().mapToBaseObject(obj, (Map<String, Object>) parameters.get("entity"));
        getOperator().save(obj, BaseOperator.CREATE_OPERA);
        return new ExecuteResult<String>().getRestString("SUCCESS");
    }

    public NamedQueryRules getNamedQueryRule() {
        if (null == this.namedQueryRule) {
            setNamedQueryRule(NamedQueryRules.getInstance());
        }
        return this.namedQueryRule;
    }

    public void setNamedQueryRule(NamedQueryRules rule) {
        this.namedQueryRule = rule;
    }

    public void setOperator(BaseOperator operator) {
        this.operator = operator;
    }

    public BaseOperator getOperator() {
        if (null == operator) {
            setOperator(BaseOperator.getInstance());
        }
        return operator;
    }
}
