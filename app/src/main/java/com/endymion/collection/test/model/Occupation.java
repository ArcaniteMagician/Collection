package com.endymion.collection.test.model;

/**
 * Created by Jim on 2018/07/30.
 */

public class Occupation {
    private String name;
    private String id;

    public Occupation() {

    }

    public Occupation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name = " + name + "}";
    }
}
