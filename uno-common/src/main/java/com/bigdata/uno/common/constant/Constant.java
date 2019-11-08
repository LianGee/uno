package com.bigdata.uno.common.constant;

public interface Constant {
    /**
     * 需求状态
     */
    interface DemandStatus {
        int CREATED = 0; // 创建
        int PLANING = 1; //规划中
        int REJECTED = 2; //已回绝
        int IMPLEMENTING = 3; //实现中
        int TO_TEST = 4; //转测试
        int TESTING = 5; //测试中
        int TO_ACCEPTANCE = 6; //转验收
        int ACCEPTED = 7; //验收通过
    }
}
