package com.sayaka.MyBatis.demo;

public class Name {
    private  Integer id;
    private  String name;
    private String email;

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
}
