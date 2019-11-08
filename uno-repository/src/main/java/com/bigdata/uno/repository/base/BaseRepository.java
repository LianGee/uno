/*
 * Copyright (C) 2018 Pinduoduo, Inc. All Rights Reserved.
 */
package com.bigdata.uno.repository.base;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.jooq.QueryPart;

import java.util.List;

/**
 * @author zaoshu
 */
public interface BaseRepository<T extends BaseEntity> {

    T selectById(Long id);

    T selectOne(QueryPart where);

    List<T> selectWhere(QueryPart where);

    List<T> selectAll();

    int insert(T entity);

    void replace(T entity);

    int updateByFields(@Param("old") T oldEntity,
                       @Param("new") T newEntity);

    int delete(Long id);

    int deleteWhere(QueryPart where);

    Page<T> selectPage(@Param("option") ListOption option,
                       @Param("pageNumKey") Long pageNum,
                       @Param("pageSizeKey") Long pageSize);
}
