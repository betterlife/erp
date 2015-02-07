package io.betterlife.erp.trigger;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.trigger.EntityTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public class PurchaseOrderSaveTrigger implements EntityTrigger {
    private static final Logger logger = LogManager.getLogger(PurchaseOrderSaveTrigger.class.getName());

    @Override
    public void action(BaseObject baseObject) {
        logger.debug("=======I am the PurchaseOrderSaveTrigger");
        logger.debug(baseObject);
    }
}
