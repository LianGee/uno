package com.bigdata.uno.service;

import com.bigdata.uno.common.model.project.Project;

public interface ProjectService {
    Long save(Project project);

    Project queryById(Long id);
}
