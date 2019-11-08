package com.bigdata.uno.common.algorithm;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DemandStatusGraph {
    private static Integer[][] graph = {
            /*创建，规划中，已回绝，实现中，转测试，测试中，转验收，验收通过 */
            /*创建*/     {0, 1, 1, 1, 0, 0, 0, 0},
            /*规划中*/   {0, 0, 1, 1, 0, 0, 0, 0},
            /*已回绝*/   {1, 0, 0, 0, 0, 0, 0, 0},
            /*实现中*/   {0, 0, 1, 0, 1, 0, 0, 0},
            /*转测试*/   {0, 0, 0, 1, 0, 1, 0, 0},
            /*测试中*/   {0, 0, 0, 0, 1, 0, 1, 0},
            /*转验收*/   {0, 0, 0, 0, 0, 1, 0, 0},
            /*验收通过*/  {0, 0, 0, 0, 0, 0, 0, 1}
    };

    public static List<Integer> getCirculateStatus(Integer status) {
        List<Integer> circulateStatus = Lists.newLinkedList();
        for (int i = 0; i < graph[status].length; i++) {
            if (graph[status][i] == 1) {
                circulateStatus.add(i);
            }
        }
        return circulateStatus;
    }
}
