package com.bigdata.uno.service;

import com.bigdata.uno.common.model.project.Project;
import com.bigdata.uno.common.model.project.ProjectPojo;

import java.util.List;

public interface ProjectService {
    Long save(Project project);

    Project queryById(Long id);

    List<ProjectPojo> queryAll();
}
