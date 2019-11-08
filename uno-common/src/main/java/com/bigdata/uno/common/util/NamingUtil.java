/*
 * Copyright (C) 2018 Pinduoduo, Inc. All Rights Reserved.
 */
package com.bigdata.uno.common.util;

//import org.springframework.util.StringUtils;

public class NamingUtil {

    public static String camelCaseToUnderscoreCase(String input) {
//        if (!StringUtils.hasText(input)) {
//            return input;
//        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append("_").append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}
