package io.betterlife.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 12/9/14
 */
public class FormConfig {
    private List<String> FormIgnoreFields;

    private static FormConfig instance = new FormConfig();

    public static FormConfig getInstance() {
        return instance;
    }

    private FormConfig() {
    }

    public List<String> getCreateFormIgnoreFields() {
        if (null == FormIgnoreFields) {
            FormIgnoreFields = new ArrayList<>(8);
            FormIgnoreFields.add("id");
            FormIgnoreFields.add("lastModifyDate");
            FormIgnoreFields.add("lastModify");
            FormIgnoreFields.add("createDate");
            FormIgnoreFields.add("creator");
        }
        return FormIgnoreFields;
    }


    public List<String> getListFormIgnoreFields() {
        if (null == FormIgnoreFields) {
            FormIgnoreFields = new ArrayList<>(4);
            FormIgnoreFields.add("lastModifyDate");
            FormIgnoreFields.add("lastModify");
            FormIgnoreFields.add("createDate");
            FormIgnoreFields.add("creator");
        }
        return FormIgnoreFields;
    }

}
