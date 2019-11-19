package com.framework.core;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Han.Sun
 */
public class GenericPo<PK extends Serializable,T extends GenericPo> extends Model<T> implements Serializable, Cloneable{
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public int hashCode() {
        if (getId() == null) return 0;
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

        }
        return super.clone();
    }

    /**
     * 自定义属性
     */
    @TableField(exist = false)
    private Map<String, Object> properties;

    public <T> T setProperty(String attr, T value) {
        if (properties == null) properties = new LinkedHashMap<>();
        properties.put(attr, value);
        return value;
    }

    public <T> T getProperty(String attr) {
        if (properties == null) return null;
        return ((T) properties.get(attr));
    }



    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

}
