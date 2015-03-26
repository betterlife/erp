package io.betterlife.framework.application.manager;

import io.betterlife.framework.annotation.EntityForm;
import io.betterlife.framework.annotation.Triggers;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.meta.EntityMeta;
import io.betterlife.framework.trigger.EntityTrigger;

import javax.persistence.metamodel.ManagedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 * Stores entity meta data, includes triggers defined in entity level, such as save trigger.
 */
public class EntityMetaDataContainer extends MetaDataContainer {

    private Map<String, EntityMeta> _entitiesMetaData = new HashMap<>();

    @Override
    public void createMeta(ManagedType managedType, Object... additionalParams) throws Exception {
        EntityMeta entityMeta = new EntityMeta();
        entityMeta.setType(managedType.getJavaType());
        Triggers triggers = (Triggers) managedType.getJavaType().getAnnotation(Triggers.class);
        if (null != triggers) {
            Class<? extends EntityTrigger> saveTrigger = triggers.Save();
            entityMeta.setSaveTrigger(saveTrigger);
        }
        EntityForm entityForm = (EntityForm) (managedType.getJavaType()).getAnnotation(EntityForm.class);
        if (null != entityForm) {
            String detailField = entityForm.DetailField();
            if (!ApplicationConfig.DefaultDetailField.equals(detailField)) {
                entityMeta.setDetailField(detailField);
            }
        }
        _entitiesMetaData.put(managedType.getJavaType().getSimpleName(), entityMeta);
    }

    @Override
    public EntityMeta get(String name) {
        return _entitiesMetaData.get(name);
    }
}
