package com.common.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yanliang.zhao on 2016-08-04.
 * 原文链接：http://www.jianshu.com/p/fa33f6267b96
 */
public class C_BuildProperties {
    private final Properties properties;

    private C_BuildProperties() throws IOException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
    }

    public boolean containsKey(final Object key) {

        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {

        return properties.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {

        return properties.entrySet();
    }

    public String getProperty(final String name) {

        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {

        return properties.getProperty(name, defaultValue);
    }

    public boolean isEmpty() {

        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {

        return properties.keys();
    }

    public Set<Object> keySet() {

        return properties.keySet();
    }

    public int size() {

        return properties.size();
    }

    public Collection<Object> values() {

        return properties.values();
    }

    public static C_BuildProperties newInstance() throws IOException {

        return new C_BuildProperties();
    }
}
