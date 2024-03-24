package com.sayaka.MyBatis.demo.entity;

public class Name {
    private  Integer id;
    private  String name;

    public  Name(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Name creatName(String name) {
        return new Name(null,name);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
