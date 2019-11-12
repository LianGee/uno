package com.bigdata.uno.common.model.requirement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementStatistic {
    private int delay;
    private int unStarted;
    private int finished;
    private int accepted;
    private int bug;
    private int total;
}
