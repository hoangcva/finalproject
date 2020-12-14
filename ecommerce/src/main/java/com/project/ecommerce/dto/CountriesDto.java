package com.project.ecommerce.dto;

import java.io.Serializable;

public class CountriesDto implements Serializable {
    private static final long serialVersionUID = -2050943448351626015L;
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
