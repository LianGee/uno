package com.bigdata.uno.repository.base;

import com.bigdata.uno.common.util.NamingUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zaoshu
 */
@Getter
public class ListOption {

    private Long pageNum;
    private Long pageSize;
    private List<Condition> conditions;
    private String search;
    private SortField<?> sortBy;
    private final Class<?> entityClass;

    public ListOption(Class<?> entityClass) {
        this.conditions = Lists.newLinkedList();
        this.entityClass = entityClass;
    }

    public static ListOption build(Class<?> entityClass) {
        return new ListOption(entityClass);
    }

    public ListOption setPageNum(Long pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public ListOption setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ListOption addCondition(Condition condition) {
        conditions.add(condition);
        return this;
    }

    public ListOption setSearch(String search) {
        this.search = search;
        return this;
    }

    public ListOption setSort(String sort, String order) {
        if (StringUtils.hasText(sort)) {
            sort = NamingUtil.camelCaseToUnderscoreCase(sort);
            setSortBy(DSL.field(sort).sort(SortOrder.valueOf(order.toUpperCase())));
        }
        return this;
    }

    public ListOption setSortBy(SortField<?> field) {
        this.sortBy = field;
        return this;
    }
}
