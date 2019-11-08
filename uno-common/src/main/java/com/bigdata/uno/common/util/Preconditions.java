/*
 * Copyright (C) 2018 Pinduoduo, Inc. All Rights Reserved.
 */
package com.bigdata.uno.common.util;

import com.bigdata.uno.common.constant.ErrorCode;
import com.bigdata.uno.common.exception.ServerException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class Preconditions {

    public static void checkNotNull(Object object, String errorMessage) {
        if (object == null) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

    public static void checkNull(Object object, String errorMessage) {
        if (object != null) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

    public static void checkNonEmpty(String text, String errorMessage) {
        if (StringUtils.isEmpty(text)) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

    public static void checkNonEmpty(Collection<?> collection, String errorMessage) {
//        if (CollectionUtils.isEmpty(collection)) {
//            throw new ServerException(errorMessage);
//        }
    }

    public static <T extends Comparable<T>> void checkBetween(T value, T min, T max, String errorMessage) {
        if (min != null && value.compareTo(min) < 0
                || max != null && value.compareTo(max) > 0) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

    public static void checkIn(Object value, String errorMessage, Object... candidates) {
        for (Object candidate : candidates) {
            if (value.equals(candidate)) {
                return;
            }
        }
        throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
    }

    public static void checkAllIn(Collection values, String errorMessage, Object... candidates) {
        for (Object value : values) {
            checkIn(value, errorMessage, candidates);
        }
    }

    public static void checkArgument(boolean arg, String errorMessage) {
        if (!arg) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

    public static void checkEqual(Object a, Object b, String errorMessage) {
        if (!Objects.equals(a, b)) {
            throw new ServerException(errorMessage, ErrorCode.CHECK_EXCEPTION.getCode());
        }
    }

}
