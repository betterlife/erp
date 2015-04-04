package io.betterlife.framework.rest;

import io.betterlife.framework.application.I18n;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.condition.Evaluator;
import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.meta.EntityMeta;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Author: Lawrence Liu
 * Date: 15/4/4
 */
@Path("/")
public class EntityMetaService {

    private static final Logger logger = LogManager.getLogger(EntityMetaService.class.getName());
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
        List<Map<String, Object>> list = getEntityMetaMap(entityType);
        Map<String, Object> entityMetaMap = new HashMap<>(2);
        entityMetaMap.put("fields", list);
        EntityMeta masterEntityMeta = MetaDataManager.getInstance().getEntityMeta(entityType);
        if (null != masterEntityMeta) {
            Map<String, Object> detailFieldsInfo = new HashMap<>(2);
            String detailField = masterEntityMeta.getDetailField();
            Class detailFieldType = masterEntityMeta.getDetailFieldType();
            if (null != detailField && null != detailFieldType) {
                detailFieldsInfo.put("fieldName", detailField);
                final List<Map<String, Object>> detailEntityMetaMap = getEntityMetaMap(detailFieldType.getSimpleName());
                detailFieldsInfo.put("fields", detailEntityMetaMap);
                entityMetaMap.put("detailField", detailFieldsInfo);
            }
        }
        String result = new ExecuteResult<Map<String, Object>>().getRestString(entityMetaMap);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entity[%s]'s meta: %n\t%s", entityType, result));
        }
        return result;
    }

    private List<Map<String, Object>> getEntityMetaMap(@PathParam("entityType") String entityType) {
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
        return list;
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
            map.put("width", 100);
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
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Enum(%s) options: %s", fieldMeta.getType(), Arrays.asList(options)));
        }
        map.put("cellTemplate", String.format(EnumCellTemplate, result.toString()));
    }

    private void setBooleanFieldAdditionalMeta(FieldMeta fieldMeta, String field, Map<String, Object> map) {
        String trueLabel = I18n.getInstance().get(fieldMeta.getTrueLabel(), ApplicationConfig.getInstance().getLocale());
        String falseLabel = I18n.getInstance().get(fieldMeta.getFalseLabel(), ApplicationConfig.getInstance().getLocale());
        map.put("cellTemplate", String.format(BooleanCellTemplate, field, trueLabel, field, falseLabel));
    }
}
