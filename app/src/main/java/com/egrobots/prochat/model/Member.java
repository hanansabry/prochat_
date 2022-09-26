package com.egrobots.prochat.model;

public class Member {

    private String id;
    private String name;
    private String code;
    private String image;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, String code) {
        this.name = name;
        this.code = code;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
