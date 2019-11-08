package com.bigdata.uno.common.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillUtil {
    public static void fillObject(Object desc, Object src) {
        List<Field> srcFields = Lists.newLinkedList();
        List<Field> desFields = Lists.newLinkedList();
        Class<?> clazz = desc.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            desFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        clazz = src.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            srcFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        Map<String, Field> descFieldMap = new HashMap<>();
        for (Field field : desFields) {
            descFieldMap.put(field.getName(), field);
        }
        try {
            for (Field field : srcFields) {
                if (descFieldMap.containsKey(field.getName())) {
                    Field descField = descFieldMap.get(field.getName());
                    descField.setAccessible(true);
                    field.setAccessible(true);
                    descField.set(desc, field.get(src));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
