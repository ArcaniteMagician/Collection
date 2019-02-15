package com.endymion.collection.apply.model.entity;

/**
 * Created by Jim on 2018/07/30.
 */

public class MainTask {
    private String title;
    private String description;
    private Class<?> forwardClass;
    private Callback callback;

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

    public Callback getCallback() {
        return callback;
    }

    public MainTask setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public interface Callback {
        void onClick();
    }
}
