package com.endymion.collection.apply.model.entity;

/**
 * Created by Jim on 2018/07/30.
 */

public class MainTask {
    private String title;
    private String description;
    private Class<?> forwardClass;

    public MainTask(String title, String description, Class<?> forwardClass) {
        this.title = title;
        this.description = description;
        this.forwardClass = forwardClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<?> getForwardClass() {
        return forwardClass;
    }

    public void setForwardClass(Class<?> forwardClass) {
        this.forwardClass = forwardClass;
    }
}
