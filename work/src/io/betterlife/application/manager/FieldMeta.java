package io.betterlife.application.manager;

/**
 * Author: Lawrence Liu
 * Date: 1/19/15
 */
public class FieldMeta {
    private String name;
    private Class type;
    private boolean editable;
    private int displayRank;
    private boolean visible;
    private String representField;
    private String aggregateType;

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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return this.visible;
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
}
