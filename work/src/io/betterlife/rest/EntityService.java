package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.application.FormConfig;
import io.betterlife.application.ServiceEntityManager;
import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.NamedQueryRules;
import io.betterlife.util.BLStringUtils;
import io.betterlife.util.EntityUtils;
import io.betterlife.util.rest.ExecuteResult;
import io.betterlife.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 11/2/14
 */
@Path("/")
@Stateless
public class EntityService {

    @PersistenceContext(unitName = ApplicationConfig.PersistenceUnitName)
    private EntityManager entityManager;

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
        Map<String, Class> meta = ServiceEntityManager.getInstance().getMetaFromEntityType(entityManager, entityType);
        List<Map<String, String>> list = new ArrayList<>(meta.size());
        for (Map.Entry<String, Class> entry : meta.entrySet()) {
            if (FormConfig.getInstance().getListFormIgnoreFields().contains(entry.getKey())){
                continue;
            }
            String field = entry.getKey();
            if (EntityUtils.getInstance().isBaseObject(entry.getValue())){
                field = EntityUtils.getInstance().getRepresentFieldWithDot(entityType, field);
            }
            Map<String, String> map = new HashMap<>();
            map.put("field", field);
            map.put("name", BLStringUtils.capitalize(entry.getKey()));
            list.add(map);
        }
        String result = new ExecuteResult<List<Map<String, String>>>().getRestString(list);
        if (logger.isTraceEnabled()){
            logger.trace("Returning \n%s\n for entity[%s] meta", result, entityType);
        }
        return result;
    }



    @GET
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObjectByTypeAndId(@PathParam("id") long id,
                                       @PathParam("entityType") String entityType) throws IOException {
        return new ExecuteResult<>().getRestString(
            getOperator().getBaseObjectById(
                entityManager, id, getNamedQueryRule().getIdQueryForEntity(entityType)
            )
        );
    }

    @GET
    @Path("/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllByObjectType(@PathParam("entityType") String entityType) throws IOException {
        List<BaseObject> result = getOperator().getBaseObjects(
            entityManager,
            getNamedQueryRule().getAllQueryForEntity(entityType)
        );
        return new ExecuteResult<List<BaseObject>>().getRestString(result);
    }

    @PUT
    @Path("/{entityType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String update(@PathParam("entityType") String entityType,
                         @PathParam("id") int id,
                         @Context HttpServletRequest request,
                         InputStream requestBody)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        Map<String, Object> entityParams = (Map<String, Object>) parameters.get("entity");
        BaseObject existingObj = getOperator().getBaseObjectById(
            entityManager, id, getNamedQueryRule().getIdQueryForEntity(entityType)
        );
        if (null != existingObj) {
            existingObj.setValues(entityManager, entityParams);
        }
        return new ExecuteResult<String>().getRestString("SUCCESS");
    }

    @POST
    @Path("/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String create(@PathParam("entityType") String entityType,
                         @Context HttpServletRequest request,
                         InputStream requestBody)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        BaseObject obj = ServiceEntityManager.getInstance().entityObjectFromType(entityType);
        EntityUtils.getInstance().mapToBaseObject(entityManager, obj, (Map<String, Object>)parameters.get("entity"));
        getOperator().save(entityManager, obj);
        return new ExecuteResult<String>().getRestString("SUCCESS");
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public NamedQueryRules getNamedQueryRule(){
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
