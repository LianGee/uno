package com.bigdata.uno.common.model.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * @return 数据库表的字段名
     */
    String name() default "";

    /**
     * @return 描述
     */
    String comment() default "";

    /**
     * @return 是否支持替换
     */
    boolean replace() default false;

    /**
     * @return 是否主键
     */
    boolean isPK() default false;

    /**
     * @return 是否分表维度
     */
    boolean isShardColumn() default false;

    /**
     * @return 是否可修改
     */
    boolean editable() default false;

    /**
     * @return 是否可用于搜索
     */
    boolean searchable() default false;

    boolean ignoreInUpdate() default false;
}
