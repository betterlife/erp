package io.betterlife.application.manager;

import io.betterlife.util.condition.Condition;
import io.betterlife.util.converter.Converter;
import org.aopalliance.reflect.Field;

/**
 * Author: Lawrence Liu
 * Date: 1/19/15
 */
public class FieldMeta {
    private String name;
    private Class type;
    private boolean editable;
    private int displayRank;
    private Class<? extends Condition> visibleClass;
    private String representField;
    private String aggregateType;
    private Class<? extends Converter> converterClass;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setDisplayRank(int displayRank) {
        this.displayRank = displayRank;
    }

    public int getDisplayRank() {
        return displayRank;
    }

    public void setVisibleCondition(Class<? extends Condition> visibleClass) {
        this.visibleClass = visibleClass;
    }

    public Class<? extends Condition> getVisibleCondition() {
        return this.visibleClass;
    }

    public void setRepresentField(String representField) {
        this.representField = representField;
    }

    public String getRepresentField() {
        return representField;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Class<? extends Converter> getConverterClass() {
        return converterClass;
    }

    public void setConverter(Class<? extends Converter> converterClass) {
        this.converterClass = converterClass;
    }
}
