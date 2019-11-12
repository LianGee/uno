package com.bigdata.uno.service;

import com.bigdata.uno.common.model.requirement.Requirement;
import com.bigdata.uno.common.model.requirement.RequirementStatistic;

import java.util.List;

public interface RequirementService {
    Requirement queryById(Long id);

    Long save(Requirement requirement);

    List<Requirement> queryAll();

    List<Requirement> queryByProjectId(Long projectId);

    RequirementStatistic statistic(Long projectId);
}