package com.framework.core.po;

/**
 * @author sunhan
 * @since 2017年02月15日
 */
public class IdText<T> {
    private String id;
    private T text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getText() {
        return text;
    }

    public void setText(T text) {
        this.text = text;
    }

    public IdText() {
    }

    public IdText(String id, T text) {
        this.id = id;
        this.text = text;
    }
}
