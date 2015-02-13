package io.betterlife.framework.application.manager;

import io.betterlife.framework.annotation.Triggers;
import io.betterlife.framework.meta.EntityMeta;
import io.betterlife.framework.trigger.EntityTrigger;

import javax.persistence.metamodel.ManagedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
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
        _entitiesMetaData.put(managedType.getJavaType().getSimpleName(), entityMeta);
    }

    @Override
    public EntityMeta get(String name) {
        return _entitiesMetaData.get(name);
    }
}
