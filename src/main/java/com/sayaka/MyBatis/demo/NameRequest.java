package com.sayaka.MyBatis.demo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NameRequest {
    private String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
