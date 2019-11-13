package com.bigdata.uno.repository.base;

import org.jooq.Field;
import org.jooq.impl.DSL;

/**
 * @author zaoshu
 */

public class Fields {
    /**
     * Base
     */
    public static Field<Long> ID = DSL.field("id", Long.class);
    public static Field<String> NAME = DSL.field("name", String.class);

    public static Field<Long> PROJECT_ID = DSL.field("project_id", Long.class);
    public static Field<String> TITLE = DSL.field("title", String.class);
    public static Field<Long> REQUIREMENT_ID = DSL.field("requirement_id", Long.class);

    public static Field<Long> CREATED_AT = DSL.field("created_at", Long.class);
    public static Field<String> CREATOR = DSL.field("creator", String.class);

}
