package com.newbie.model;

import java.io.Serializable;

/**
 * Created by pan on 2018/11/15 上午1:26
 */
public class YicheCity implements Serializable {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String name;

    public YicheCity() {
    }

    public YicheCity(Integer id, Integer parentId, Integer type, String name) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public YicheCity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public YicheCity setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public YicheCity setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public YicheCity setName(String name) {
        this.name = name;
        return this;
    }
}

