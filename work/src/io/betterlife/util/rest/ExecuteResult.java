package io.betterlife.util.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.jms.MapMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExecuteResult<T> implements Serializable {

    private static final long serialVersionUID = 5575458930882890461L;

    private static final Logger logger = LogManager.getLogger(ExecuteResult.class.getName());

    /**
     * 返回结果数据
     */
    private T result;

    /**
     * 成功提示消息
     */
    private String successMessage;

    /**
     * 普通的错误信息
     */
    private List<String> errorMessages = new ArrayList<String>();

    /**
     * 判断当前执行结果是否正确，如果errorMessages和fieldErrors都为空，则无错
     *
     * @return 如果成功返回 true, 如果失败返回false
     */
    public boolean isSuccess() {
        return errorMessages.isEmpty();
    }

    /**
     * 添加一条错误消息到列表中
     *
     * @param errorMessage 具体的错误信息
     */
    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public static String getRestString(Object object) {
        ExecuteResult<Object> result = new ExecuteResult<>();
        ObjectMapper mapper = new ObjectMapper();
        String resultStr = "";
        try {
            if (null != object) {
                result.setResult(object);
            } else {
                result.setResult(null);
                result.addErrorMessage("Object is null");
            }
            resultStr = mapper.writeValueAsString(result);
        } catch (Exception e) {
            logger.error("Exception writeValueAsString");
            logger.error("Parameter object:" + object);
            logger.error("Exception detail", e);
            result.setResult(null);
            result.addErrorMessage(e.toString());
        }
        return resultStr;
    }
}
