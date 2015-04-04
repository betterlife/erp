package io.betterlife.framework.rest;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.meta.EntityMeta;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.condition.Evaluator;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.EntityUtils;
import io.betterlife.framework.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/2/14
 */
@Path("/")
public class EntityService {

    private static final Logger logger = LogManager.getLogger(EntityService.class.getName());
    public static final String BooleanCellTemplate = "<div class='ui-grid-cell-contents'><span ng-show='row.entity.%s'>%s</span><span ng-show='!row.entity.%s'>%s</span></div>";
    public static final String EnumCellTemplate = "<div class='ui-grid-cell-contents'>%s</div>";


    /**
     * Get entity meta data(for render the entity list page)
     * Please see document of grid-ui to find detail form.
     * Something like
     * <pre>
     * [
     *   {name: 'firstName', field: 'first-name'},
     *   {name: '1stFriend', field: 'friends[0]'},
     *   {name: 'city', field: 'address.city'},
     *   {name: 'getZip', field: 'getZip()', enableCellEdit: false}
     * ]
     * </pre>
     * @param entityType Entity type, start with with capitalize letter
     * @return a Json represent of the entity meta data, in the way grid-ui respects.
     */
    @GET
    @Path("/entity/{entityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEntityMeta(@PathParam("entityType") String entityType) throws NoSuchMethodException {
        if (logger.isTraceEnabled()) {
            logger.trace("Getting entity meta data for " + entityType);
        }
        Map<String, FieldMeta> meta = MetaDataManager.getInstance().getMetaFromEntityType(entityType);
        LinkedHashMap<String, FieldMeta> sortedMeta = EntityUtils.getInstance().sortEntityMetaByDisplayRank(meta);
        List<Map<String, Object>> list = new ArrayList<>(sortedMeta.size());
        for (Map.Entry<String, FieldMeta> entry : sortedMeta.entrySet()) {
            final FieldMeta fieldMeta = entry.getValue();
            if (!Evaluator.getInstance().evalVisible(entityType, fieldMeta, null, Operation.LIST)) continue;
            String field = entry.getKey();
            if (EntityUtils.getInstance().isBaseObject(fieldMeta.getType())) {
                field = EntityUtils.getInstance().getRepresentFieldWithDot(fieldMeta);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("field", field);
            setFieldAdditionalMeta(fieldMeta, field, map);
            setAggregationType(field, map);
            setFieldFixWidth(fieldMeta, map);
            map.put("name", I18n.getInstance().getFieldLabel(entityType, entry.getKey()));
            list.add(map);
        }
        Map<String, Object> entityMetas = new HashMap<>(2);
        entityMetas.put("fields", list);
        EntityMeta entityMeta = MetaDataManager.getInstance().getEntityMeta(entityType);
        if (null != entityMeta) {
            Map<String, Object> detailFieldsInfo = new HashMap<>(2);
            String detailField = entityMeta.getDetailField();
            Method m = entityMeta.getType().getMethod("get" + BLStringUtils.capitalize(detailField));
            if (m != null) {
                Class detailClazz = m.getReturnType();
                TypeVariable[] typeVariables = detailClazz.getTypeParameters();
                TypeVariable type = typeVariables[0];
                GenericDeclaration genericDeclaration = type.getGenericDeclaration();
                detailFieldsInfo.put("fieldName", detailField);
                final String detailEntity = getEntityMeta(detailClazz.getSimpleName());
                detailFieldsInfo.put("fields", detailEntity);
                entityMetas.put("detailFieldsInfo", detailFieldsInfo);
            }
        }
        String result = new ExecuteResult<Map<String, Object>>().getRestString(entityMetas);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entity[%s]'s meta: %n\t%s", entityType, result));
        }
        return result;
    }

    public void setFieldAdditionalMeta(FieldMeta fieldMeta, String field, Map<String, Object> map) {
        if (EntityUtils.getInstance().isBooleanField(fieldMeta)) {
            setBooleanFieldAdditionalMeta(fieldMeta, field, map);
        }
        if (EntityUtils.getInstance().isEnumField(fieldMeta)){
            setEnumFieldAdditionalMeta(fieldMeta, field, map);
        }
    }

    public void setAggregationType(String field, Map<String, Object> map) {
        if (BLStringUtils.containsIgnoreCase(field, "amount")) {
            map.put("aggregationType", 2);
        }
    }

    private void setFieldFixWidth(FieldMeta fieldMeta, Map<String, Object> map) {
        if (EntityUtils.getInstance().isDateField(fieldMeta)) {
            map.put("width", 100);
        }
        if (EntityUtils.getInstance().isDecimalField(fieldMeta)) {
            map.put("width", 80);
        }
        if (EntityUtils.getInstance().isIdField(fieldMeta.getName())) {
            map.put("width", 60);
        }
        if (EntityUtils.getInstance().isUserField(fieldMeta)) {
            map.put("width", 80);
        }
        if (EntityUtils.getInstance().isRemarkField(fieldMeta)) {
            map.put("width", 350);
        }
    }

    private void setEnumFieldAdditionalMeta(FieldMeta fieldMeta, String field, Map<String, Object> map) {
        String template = "<span ng-show=\"row.entity.%s=='%s'\">%s</span>";
        StringBuilder result = new StringBuilder();
        Object[] options = fieldMeta.getType().getEnumConstants();
        for (Object option : options) {
            final String translate = I18n.getInstance().get(option.toString(), ApplicationConfig.getInstance().getLocale());
            result.append(String.format(template, field, option, translate)).append("\n");
        }
        logger.debug("Enum(%s) options: %s", fieldMeta.getType(), options);
        map.put("cellTemplate", String.format(EnumCellTemplate, result.toString()));
    }

    private void setBooleanFieldAdditionalMeta(FieldMeta fieldMeta, String field, Map<String, Object> map) {
        String trueLabel = I18n.getInstance().get(fieldMeta.getTrueLabel(), ApplicationConfig.getInstance().getLocale());
        String falseLabel = I18n.getInstance().get(fieldMeta.getFalseLabel(), ApplicationConfig.getInstance().getLocale());
        map.put("cellTemplate", String.format(BooleanCellTemplate, field, trueLabel, field, falseLabel));
    }

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
        Map<String, Object> parameters = IOUtil.getInstance().inputStreamToJson(requestBody);
        Map<String, Object> entityParams = (Map<String, Object>) parameters.get("entity");
        BaseObject existingObj = getBaseOperator().getBaseObjectById(
            id, getNamedQueryRule().getIdQueryForEntity(entityType)
        );
        if (null != existingObj) {
            existingObj.setValues(entityParams);
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
