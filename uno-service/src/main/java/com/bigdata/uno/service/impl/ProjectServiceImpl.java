package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.project.Project;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.ProjectRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Long save(Project project) {
        Preconditions.checkNotNull(project.getName(), "服务名不可为空");
        if (project.getId() != null) {

        }
        projectRepository.insert(project);
        return projectRepository.selectOne(Fields.NAME.eq(project.getName())).getId();
    }

    @Override
    public Project queryById(Long id) {
        return projectRepository.selectById(id);
    }
}
