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
    public static Field<Boolean> IS_ADMIN = DSL.field("is_admin", Boolean.class);

}
