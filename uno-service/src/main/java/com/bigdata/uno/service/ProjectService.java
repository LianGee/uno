package com.bigdata.uno.service;

import com.bigdata.uno.common.model.project.Project;

import java.util.List;

public interface ProjectService {
    Long save(Project project);

    Project queryById(Long id);

    List<Project> queryAll();
}
