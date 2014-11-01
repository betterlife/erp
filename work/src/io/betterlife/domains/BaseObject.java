package io.betterlife.domains;

import io.betterlife.domains.security.User;

import java.util.Date;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class BaseObject {

    private Date lastModifyDate;
    private User lastModify;
    private Date createDate;
    private User creator;

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModify(User lastModify) {
        this.lastModify = lastModify;
    }

    public User getLastModify() {
        return lastModify;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }
}
