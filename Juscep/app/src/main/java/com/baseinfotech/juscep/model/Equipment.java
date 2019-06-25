package com.baseinfotech.juscep.model;


public class Equipment {

    private String id, name;
    //private String slug, description, created_at, updated_at, created_by, deleted_at, status;

    public Equipment(){
        id = "-1";
        name = "Select Equipment";
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
}
