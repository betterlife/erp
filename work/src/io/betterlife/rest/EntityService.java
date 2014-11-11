package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.MetaDataManager;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.util.rest.ExecuteResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Map<String, Class> classes = new HashMap<>();

    private static Class getServiceEntity(String name) {
        return classes.get(name);
    }

    public static void registerServiceEntity(String name, Class clazz) {
        classes.put(name, clazz);
    }

    @GET
    @Path("/entity/{entityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEntityMeta(@PathParam("entityName") String entityName) throws IOException {
        logger.debug("Getting entity meta data for " + entityName);
        entityName = StringUtils.uncapitalize(entityName);
        MetaDataManager.getInstance().setAllFieldMetaData(entityManager);
        Map<String, Class> meta = MetaDataManager.getInstance().getMetaDataOfClass(getServiceEntity(entityName));
        String result = ExecuteResult.getRestString(meta);
        if (logger.isTraceEnabled()){
            logger.trace("Returning \n%s\n for entity[%s] meta", result, entityName);
        }
        return result;
    }


    @GET
    @Path("/{objectType}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObjectByTypeAndId(@PathParam("id") long id,
                                       @PathParam("objectType") String objectType) throws IOException {
        return get(entityManager, id, objectType + "." + "getById");
    }

    @GET
    @Path("/{objectType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllByObjectType(@PathParam("objectType") String objectType) throws IOException {
        return getAll(entityManager, objectType + "." + "getAll");
    }

    @POST
    @Path("/{objectType}")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String create(@PathParam("objectType") String objectType, MultivaluedMap<String, String> formParams)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Map<String, String> parameters = new HashMap<>();
        for (String theKey : formParams.keySet()) {
            parameters.put(theKey, URLDecoder.decode(formParams.getFirst(theKey), "UTF-8"));
        }
        Class clazz = getServiceEntity(objectType);
        Object obj = clazz.newInstance();
        if (obj instanceof BaseObject) {
            ((BaseObject) obj).setValues(entityManager, parameters);
            BaseOperator.getInstance().save(entityManager, obj);
        }
        return ExecuteResult.getRestString("SUCCESS");
    }

    public String get(EntityManager entityManager, long id, String queryName) throws IOException {
        return ExecuteResult.getRestString(BaseOperator.getInstance().getBaseObjectById(entityManager, id, queryName));
    }

    public String getAll(EntityManager entityManager, String queryName) throws IOException {
        List<Object> result = BaseOperator.getInstance().getBaseObjects(entityManager, queryName);
        return ExecuteResult.getRestString(result);
    }
}
