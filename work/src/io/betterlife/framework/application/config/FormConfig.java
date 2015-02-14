package io.betterlife.framework.application.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 12/9/14
 */
public class FormConfig {
    private List<String> createFormIgnoreFields;
    private List<String> listFormIgnoreFields;
    private List<String> editFormIgnoreFields;

    private static FormConfig instance = new FormConfig();

    public static FormConfig getInstance() {
        return instance;
    }

    public static void setInstance(FormConfig instance) {
        FormConfig.instance = instance;
    }

    private FormConfig() {
    }

    public List<String> getCreateFormIgnoreFields() {
        if (null == createFormIgnoreFields) {
            createFormIgnoreFields = new ArrayList<>(8);
            createFormIgnoreFields.add("id");
            createFormIgnoreFields.add("lastModifyDate");
            createFormIgnoreFields.add("lastModify");
            createFormIgnoreFields.add("createDate");
            createFormIgnoreFields.add("creator");
            createFormIgnoreFields.add("active");
        }
        return createFormIgnoreFields;
    }


    public List<String> getListFormIgnoreFields() {
        if (null == listFormIgnoreFields) {
            listFormIgnoreFields = new ArrayList<>(4);
            listFormIgnoreFields.add("lastModifyDate");
            listFormIgnoreFields.add("lastModify");
            listFormIgnoreFields.add("createDate");
            listFormIgnoreFields.add("creator");
            listFormIgnoreFields.add("active");
            listFormIgnoreFields.add("password");
        }
        return listFormIgnoreFields;
    }

    public List<String> getEditFormIgnoreFields() {
        if (null == editFormIgnoreFields) {
            editFormIgnoreFields = new ArrayList<>(4);
            editFormIgnoreFields.add("lastModifyDate");
            editFormIgnoreFields.add("lastModify");
            editFormIgnoreFields.add("createDate");
            editFormIgnoreFields.add("creator");
            editFormIgnoreFields.add("active");
        }
        return editFormIgnoreFields;
    }
}
