package com.endymion.collection.apply.model.entity;

/**
 * Created by Jim on 2018/07/30.
 */

public class MainTask {
    private String title;
    private String description;
    private Class<?> forwardClass;
    private int componentIndex;

    public MainTask(String title, String description) {
        this.title = title;
        this.description = description;
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

    public MainTask setForwardClass(Class<?> forwardClass) {
        this.forwardClass = forwardClass;
        return this;
    }

    public int getComponentIndex() {
        return componentIndex;
    }

    public MainTask setComponentIndex(int componentIndex) {
        this.componentIndex = componentIndex;
        return this;
    }
}
