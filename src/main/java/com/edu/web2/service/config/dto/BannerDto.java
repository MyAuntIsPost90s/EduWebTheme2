package com.edu.web2.service.config.dto;

public class BannerDto {
	
    /**
     * 主键：id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String picUrl;
    
    /**
     * 图片路径不包含前缀
     */
    private String picUrlWithOutPrefix;

    /**
     * 排序号
     */
    private Integer sortIndex;

    /**
     * 链接
     */
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public String getPicUrlWithOutPrefix() {
		return picUrlWithOutPrefix;
	}
    
    public void setPicUrlWithOutPrefix(String picUrlWithOutPrefix) {
		this.picUrlWithOutPrefix = picUrlWithOutPrefix;
	}
}