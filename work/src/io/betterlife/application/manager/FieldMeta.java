package io.betterlife.application.manager;

/**
 * Author: Lawrence Liu
 * Date: 1/19/15
 */
public class FieldMeta {
    private String name;
    private Class type;
    private boolean editable;

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
}
