package com.bigdata.uno.common.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelUtil {
    public static void modelToPojo(Object model, Object pojo) {
        if (model == null || pojo == null) {
            return;
        }
        List<Field> modelFields = getAllFields(model.getClass());
        Map<String, Field> modelMap = new HashMap<>();
        modelFields.forEach(field -> modelMap.put(field.getName(), field));
        try {
            for (Field field : getAllFields(pojo.getClass())) {
                field.setAccessible(true);
                JSONColumn jsonColumn = field.getAnnotation(JSONColumn.class);
                Field modelField = modelMap.get(field.getName());
                modelField.setAccessible(true);
                if (jsonColumn != null) {
                    field.set(pojo, JSON.parseObject((String) modelField.get(model), field.getGenericType()));
                } else {
                    field.set(pojo, modelField.get(model));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void pojoToModel(Object pojo, Object model) {
        if (model == null || pojo == null) {
            return;
        }
        List<Field> modelFields = getAllFields(model.getClass());
        Map<String, Field> modelMap = new HashMap<>();
        modelFields.forEach(field -> modelMap.put(field.getName(), field));
        try {
            for (Field field : getAllFields(pojo.getClass())) {
                if ("updatedAt".equals(field.getName())
                        || "createdAt".equals(field.getName())
                        || "isDelete".equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                JSONColumn jsonColumn = field.getAnnotation(JSONColumn.class);
                Field modelField = modelMap.get(field.getName());
                modelField.setAccessible(true);
                if (jsonColumn != null) {
                    modelField.set(model, JSON.toJSONString(field.get(pojo)));
                } else {
                    modelField.set(model, field.get(pojo));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == Object.class) {
            return Lists.newArrayList();
        }
        List<Field> fields = Lists.newLinkedList();
        Collections.addAll(fields, clazz.getDeclaredFields());
        fields.addAll(getAllFields(clazz.getSuperclass()));
        return fields;
    }
}
