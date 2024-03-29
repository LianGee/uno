package com.bigdata.uno.service;

import com.bigdata.uno.common.model.information.Info;
import com.bigdata.uno.common.model.requirement.Requirement;
import com.bigdata.uno.common.model.requirement.RequirementStatistic;
import com.bigdata.uno.common.model.requirement.UpdateRequirement;

import java.util.List;

public interface RequirementService {
    Requirement queryById(Long id);

    Long save(Requirement requirement, String userName);

    List<Requirement> queryAll();

    List<Requirement> queryByProjectId(Long projectId);

    RequirementStatistic statistic(Long projectId);

    boolean statusFlow(UpdateRequirement updateRequirement);

    boolean updateDate(UpdateRequirement updateRequirement);

    List<Info> queryComments(Long id);

    Long addComment(Info info, String userName);
}
