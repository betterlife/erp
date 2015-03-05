package io.betterlife.framework.application.manager;

import io.betterlife.framework.domains.BaseObject;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Set;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 * Abstract class for all the meta data.
 */
public abstract class MetaDataContainer {

    private static final Logger logger = LogManager.getLogger(MetaDataContainer.class.getName());
    private boolean hasMeta = false;
    public abstract void createMeta(ManagedType managedType, Object... additionalParams) throws Exception;
    public abstract Object get(String name);

    public boolean hasMeta() {
        return hasMeta;
    }

    public void setHasMeta(boolean hasMeta) {
        this.hasMeta = hasMeta;
    }

    public void loadMeta(EntityManager entityManager) {
        if (!hasMeta()) {
            synchronized (this) {
                if (hasMeta()) {
                    return;
                }
                try {
                    Metamodel metaModel = entityManager.getMetamodel();
                    Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
                    for (ManagedType managedType : managedTypes) {
                        @SuppressWarnings("unchecked")
                        Class<? extends BaseObject> clazz = managedType.getJavaType();
                        List<Class<?>> ignoreClasses = ClassUtils.getAllSuperclasses(BaseObject.class);
                        if (clazz.equals(BaseObject.class) || (ignoreClasses != null && ignoreClasses.contains(clazz))) {
                            continue;
                        }
                        createMeta(managedType, clazz);
                    }
                    setHasMeta(true);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

}
