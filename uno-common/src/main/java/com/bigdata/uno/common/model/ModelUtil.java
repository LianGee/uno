package com.bigdata.uno.common.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelUtil {
    public static void poJoToModel(Object pojo, Object model) {
        if (pojo == null || model == null) {
            return;
        }
        List<Field> pojoFields = getAllFields(pojo.getClass());
        Map<String, Field> modelMap = new HashMap<>();
        pojoFields.forEach(field -> modelMap.put(field.getName(), field));
        try {
            for (Field field : getAllFields(model.getClass())) {
                field.setAccessible(true);
                JSONColumn jsonColumn = field.getAnnotation(JSONColumn.class);
                Field modelField = modelMap.get(field.getName());
                if (modelField == null || !modelField.getName().equals(field.getName())) {
                    continue;
                }
                modelField.setAccessible(true);
                if (jsonColumn != null) {
                    field.set(model, JSON.parseObject((String) modelField.get(pojo), field.getGenericType()));
                } else {
                    field.set(model, modelField.get(pojo));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void modelToPoJO(Object model, Object pojo) {
        if (pojo == null || model == null) {
            return;
        }
        List<Field> modelFields = getAllFields(pojo.getClass());
        Map<String, Field> modelMap = new HashMap<>();
        modelFields.forEach(field -> modelMap.put(field.getName(), field));
        try {
            for (Field field : getAllFields(model.getClass())) {
                if ("updatedAt".equals(field.getName())
                        || "createdAt".equals(field.getName())
                        || "isDelete".equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                JSONColumn jsonColumn = field.getAnnotation(JSONColumn.class);
                Field modelField = modelMap.get(field.getName());
                if (modelField == null || !modelField.getName().equals(field.getName())) {
                    continue;
                }
                modelField.setAccessible(true);
                if (jsonColumn != null) {
                    modelField.set(pojo, JSON.toJSONString(field.get(model)));
                } else {
                    modelField.set(pojo, field.get(model));
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
