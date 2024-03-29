package com.sayaka.MyBatis.demo;

public class NameSearchRequest {
    private String startsWith;
    private String endsWith;

    public NameSearchRequest(String startsWith, String endsWith) {
        this.startsWith = startsWith;
        this.endsWith = endsWith;
    }

    public String getStartsWith() {
        return startsWith == null ? "" : startsWith ;
    }

    public String getEndsWith() {

        return endsWith == null ? "" : endsWith ;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }
}
