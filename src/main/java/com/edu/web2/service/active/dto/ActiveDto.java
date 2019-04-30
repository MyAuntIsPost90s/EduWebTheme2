package com.edu.web2.service.active.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ActiveDto {
	/**
     * 主键：id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 活动封面图片
     */
    private String picUrl;
    
    /**
     * 活动封面图片（不包含前缀）
     */
    private String picUrlNoPrefix;

    /**
     * 活动状态（1报名中 2已结束）
     */
    private Integer status;

    /**
     * 内容
     */
    private String content;
    
    /**
     * 报名人数
     */
    private Integer joinCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getPicUrlNoPrefix() {
		return picUrlNoPrefix;
	}
    
    public void setPicUrlNoPrefix(String picUrlNoPrefix) {
		this.picUrlNoPrefix = picUrlNoPrefix;
	}
    
    public Integer getJoinCount() {
		return joinCount;
	}
    
    public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
}
