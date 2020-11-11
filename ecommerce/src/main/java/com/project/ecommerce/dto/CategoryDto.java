package com.project.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4275928225304177299L;
    private Integer id;
    private String name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
