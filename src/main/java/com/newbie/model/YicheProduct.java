package com.newbie.model;

import java.io.Serializable;

/**
 * Created by pan on 2018/11/15 上午1:26
 */
public class YicheProduct implements Serializable {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String name;

    public YicheProduct() {
    }

    public YicheProduct(Integer id, Integer parentId, Integer type, String name) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public YicheProduct setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public YicheProduct setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public YicheProduct setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public YicheProduct setName(String name) {
        this.name = name;
        return this;
    }
}

