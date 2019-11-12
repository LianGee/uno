package com.bigdata.uno.common.constant;

public interface Constant {
    /**
     * 需求状态
     */
    interface RequirementStatus {
        int CREATED = 0; // 新创建
        int REJECTED = 1; //已回绝
        int IMPLEMENTING = 2; //实现中
        int TO_TEST = 3; //转测试
        int TESTING = 4; //测试中
        int TO_CHECK = 5; //转验收
        int CHECKED = 6; //验收通过
        int ACCEPTED = 7; //已交付
    }

    interface RequirementType {
        int OPTIMIZATION = 0; //优化
        int FEATURE = 1; //新特性
        int BUG = 2; //缺陷
    }

    String MD5_SALT = "asdf";
}
