package com.bigdata.uno.repository.base;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import com.bigdata.uno.common.util.DateTimeUtil;
import com.bigdata.uno.repository.excption.RepositoryException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.jdbc.SQL;
import org.jooq.QueryPart;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractSqlProvider {
    /**
     * @return 数据表名
     */
    public abstract String table();

    public boolean hasIsDeleted() {
        return true;
    }

    public boolean hasUpdatedAt() {
        return true;
    }

    public boolean hasCreatedAt() {
        return true;
    }

    public String orderBy() {
        return "`id` DESC";
    }

    public String insert(Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity base = (BaseEntity) entity;
            if (hasCreatedAt() && base.getCreatedAt() == null) {
                base.setCreatedAt(DateTimeUtil.unixTimestamp());
            }
            if (hasUpdatedAt() && base.getUpdatedAt() == null) {
                base.setUpdatedAt(DateTimeUtil.unixTimestamp());
            }
            if (hasIsDeleted()) {
                base.setIsDelete(false);
            }
        }
        return new SQL() {
            {
                INSERT_INTO(table());
                for (Field field : getAllFields(entity.getClass())) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    try {
                        field.setAccessible(true);
                        if (field.get(entity) == null) {
                            continue;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    Column column = field.getAnnotation(Column.class);
                    if (column.ignoreInUpdate() || column.isPK()
                            || !hasUpdatedAt() && "updatedAt".equals(field.getName())
                            || !hasCreatedAt() && "createdAt".equals(field.getName())
                            || !hasIsDeleted() && "isDelete".equals(field.getName())) {
                        continue;
                    }
                    VALUES(getColumnName(column, field), quoteVariable(field.getName()));
                }
            }
        }.toString();
    }

    public String replace(Object entity) {
        return insert(entity).replace("INSERT INTO", "REPLACE INTO");
    }

    public String delete(Object entity) {
        return new SQL() {
            {
                DELETE_FROM(table());
                WHERE(String.join(" AND ", getKeyConditions(entity)));
            }
        }.toString();
    }

    public String deleteWhere(Object whereObject) {
        String where = whereObject.toString();
        return new SQL() {
            {
                DELETE_FROM(table());
                WHERE(where);
            }
        }.toString();
    }

    public String update(Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity base = (BaseEntity) entity;
            if (hasUpdatedAt() && base.getUpdatedAt() == null) {
                base.setUpdatedAt(DateTimeUtil.unixTimestamp());
            }
        }
        return new SQL() {
            {
                UPDATE(table());
                for (String set : getUpdateFields(null, entity)) {
                    SET(set);
                }
                WHERE(String.join(" AND ", getKeyConditions(entity)));
            }
        }.toString();
    }

    public String updateNotNullFields(Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity base = (BaseEntity) entity;
            if (hasUpdatedAt() && base.getUpdatedAt() == null) {
                base.setUpdatedAt(DateTimeUtil.unixTimestamp());
            }
        }
        return new SQL() {
            {
                UPDATE(table());
                for (String set : getUpdateNonNullFields(entity)) {
                    SET(set);
                }
                WHERE(String.join(" AND ", getKeyConditions(entity)));
            }
        }.toString();
    }

    public String updateByFields(Map<String, Object> map) {
        Object oldEntity = map.get("old");
        Object newEntity = map.get("new");
        if (newEntity instanceof BaseEntity) {
            BaseEntity base = (BaseEntity) newEntity;
            base.setUpdatedAt(DateTimeUtil.unixTimestamp());
        }
        return new SQL() {
            {
                UPDATE(table());
                for (String set : getUpdateFields(oldEntity, newEntity)) {
                    SET(set);
                }
                WHERE(String.join(" AND ", getKeyConditions(oldEntity)));
            }
        }.toString();
    }

    public String select(Object entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                WHERE(String.join(" AND ", getKeyConditions(entity)));
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString() + " LIMIT 1";
    }


    public String softDelete(Long id) {
        return new SQL() {
            {
                UPDATE(table());
                if (hasIsDeleted()) {
                    SET(createEqualExpression("`is_delete`", 1));
                }
                WHERE("`id` = #{id}");
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString();
    }

    public String softDeleteWhere(Object whereObject) {
        String where = whereObject.toString();
        return new SQL() {
            {
                UPDATE(table());
                if (hasIsDeleted()) {
                    SET(createEqualExpression("`is_delete`", 1));
                }
                WHERE(where);
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString();
    }

    public String selectById(Long id) {
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                WHERE("`id` = #{id}");
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString() + " LIMIT 1";
    }

    public String selectOne(Object whereObject) {
        String where = whereObject.toString();
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                if (StringUtils.hasText(where)) {
                    WHERE(where);
                }
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString() + " LIMIT 1";
    }

    public String count(Object whereObject) {
        String where = whereObject.toString();
        return new SQL() {
            {
                SELECT("count(1)");
                FROM(table());
                if (StringUtils.hasText(where)) {
                    WHERE(where);
                }
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
            }
        }.toString() + " LIMIT 1";
    }

    public String selectAll() {
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                if (hasIsDeleted()) {
                    WHERE("`is_delete` = 0");
                }
                //                ORDER_BY(orderBy());
            }
        }.toString();
    }

    public String selectWhere(Object whereObject) {
        String where = whereObject.toString();
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                if (StringUtils.hasText(where)) {
                    WHERE(where);
                }
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
                //                ORDER_BY(orderBy());
            }
        }.toString();
    }

    @SuppressWarnings("unchecked")
    public String selectPage(Map<String, Object> map) {
        ListOption option = (ListOption) map.get("option");
        String where = option.getConditions().stream().map(QueryPart::toString).collect(Collectors.joining(" AND "));
        Class<?> entityClass = option.getEntityClass();
        return new SQL() {
            {
                SELECT("*");
                FROM(table());
                if (StringUtils.hasText(where)) {
                    WHERE(where);
                }
                if (StringUtils.hasText(option.getSearch())) {
                    AND().WHERE(getSearchCondition(entityClass, option.getSearch()));
                }
                if (hasIsDeleted()) {
                    AND().WHERE("`is_delete` = 0");
                }
                org.jooq.SortField<?> sortField = option.getSortBy();
                if (sortField != null) {
                    ORDER_BY(sortField.toString());
                }
            }
        }.toString();
    }

    private static <T> String getSearchCondition(Class<T> entityClass, String search) {
        List<String> values = Lists.newLinkedList();
        for (Field field : entityClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            if (!column.searchable()) {
                continue;
            }
            values.add(String.format("%s LIKE '%%%s%%'", getColumnName(column, field),
                    StringEscapeUtils.escapeSql(search)));
        }
        return String.join(" OR ", values);
    }

    private static <T> List<String> getKeyConditions(T entity) {
        boolean hasPrimaryKey = false;
        List<String> conditions = Lists.newLinkedList();
        try {
            for (Field field : getAllFields(entity.getClass())) {
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                if (column.isShardColumn()) {
                    field.setAccessible(true);
                    conditions.add(createEqualExpression(getColumnName(column, field), field.get(entity)));
                    hasPrimaryKey = true;
                }
            }
            if (!hasPrimaryKey) {
                for (Field field : getAllFields(entity.getClass())) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    Column column = field.getAnnotation(Column.class);
                    if (column.isPK()) {
                        field.setAccessible(true);
                        conditions.add(createEqualExpression(getColumnName(column, field), field.get(entity)));
                        hasPrimaryKey = true;
                    }
                }
            }
            if (!hasPrimaryKey) {
                throw new RepositoryException("缺少主键字段");
            }
            return conditions;
        } catch (IllegalAccessException e) {
            throw new RepositoryException("Failed to selectById key fields from " + entity, e);
        }
    }

    private <T> List<String> getUpdateFields(T oldEntity, T newEntity) {
        boolean hasUpdateField = false;
        List<String> values = Lists.newLinkedList();
        try {
            for (Field field : getAllFields(newEntity.getClass())) {
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                if (column.ignoreInUpdate()
                        || !hasUpdatedAt() && "updatedAt".equals(field.getName())
                        || !hasCreatedAt() && "createdAt".equals(field.getName())
                        || !hasIsDeleted() && "isDelete".equals(field.getName())) {
                    continue;
                }
                ReflectionUtils.makeAccessible(field);
                field.setAccessible(true);
                if (oldEntity == null || !Objects.equals(field.get(newEntity), field.get(oldEntity))) {
                    values.add(createEqualExpression(getColumnName(column, field), field.get(newEntity)));
                    hasUpdateField = true;
                }
            }
            if (hasUpdatedAt() && !hasUpdateField) {
                values.add("updated_at = " + DateTimeUtil.getCurrentDateTime());
            }
            return values;
        } catch (IllegalAccessException e) {
            throw new RepositoryException("Failed to get update fields from " + oldEntity, e);
        }
    }

    private <T> List<String> getUpdateNonNullFields(T newEntity) {
        boolean hasUpdateField = false;
        List<String> values = Lists.newLinkedList();
        try {
            for (Field field : getAllFields(newEntity.getClass())) {
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                if (column.ignoreInUpdate()
                        || !hasUpdatedAt() && "updatedAt".equals(field.getName())
                        || !hasCreatedAt() && "createdAt".equals(field.getName())
                        || !hasIsDeleted() && "isDelete".equals(field.getName())) {
                    continue;
                }
                ReflectionUtils.makeAccessible(field);
                field.setAccessible(true);
                if (field.get(newEntity) != null) {
                    values.add(createEqualExpression(getColumnName(column, field), field.get(newEntity)));
                    hasUpdateField = true;
                }
            }
            if (hasUpdatedAt() && !hasUpdateField) {
                values.add("updated_at = " + DateTimeUtil.getCurrentDateTime());
            }
            return values;
        } catch (IllegalAccessException e) {
            throw new RepositoryException("Failed to get update fields from " + newEntity, e);
        }
    }

    private static String getColumnName(Column column, Field field) {
        if (StringUtils.isEmpty(column.name())) {
            return quoteField(field.getName());
        } else {
            return quoteField(column.name());
        }
    }

    private static String quoteVariable(String field) {
        return "#{" + field + "}";
    }

    private static String quoteField(String field) {
        field = field.toLowerCase();
        if (field.contains(".") || field.contains("`")) {
            return field;
        }
        return "`" + field + "`";
    }

    private static String createEqualExpression(String name, Object value) {
        if (value == null) {
            return String.format("%s = NULL", name);

        } else if (value instanceof Boolean) {
            return String.format("%s = %d", name, Boolean.TRUE.equals(value) ? 1 : 0);

        } else if (value instanceof Number) {
            return String.format("%s = %s", name, value);

        } else {
            return String.format("%s = '%s'", name, StringEscapeUtils.escapeSql(value.toString()));
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
