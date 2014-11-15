package io.betterlife.util.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
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
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断当前执行结果是否正确，如果errorMessages和fieldErrors都为空，则无错
     *
     * @return 如果成功返回 true, 如果失败返回false
     */
    public boolean isSuccess() {
        return errorMessages.isEmpty();
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

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public String getRestString(T object) {
        setResult(object);
        String resultStr = "";
        try {
            resultStr = objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            logWriteValueAsStringError(object, e);
        }
        return (null == resultStr) ? "" : resultStr;
    }

    private void logWriteValueAsStringError(T object, Exception e) {
        logger.error("Exception writeValueAsString");
        logger.error("Parameter object:" + object);
        logger.error("Exception detail", e);
    }

    public void setObjectMapper(ObjectMapper mapper){
        this.objectMapper = mapper;
    }
}
