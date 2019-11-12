package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.common.model.project.ProjectPoJo;
import com.bigdata.uno.common.model.project.Project;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.ProjectRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.BusinessService;
import com.bigdata.uno.service.ProjectService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BusinessService businessService;

    @Override
    public Long save(Project project) {
        Preconditions.checkNotNull(project.getName(), "服务名不可为空");
        ProjectPoJo projectPoJo = ProjectPoJo.builder().build();
        ModelUtil.modelToPoJO(project, projectPoJo);
        if (project.getId() != null) {
            projectRepository.updateNotNullFields(projectPoJo);
            return project.getId();
        }
        projectRepository.insert(projectPoJo);
        return projectRepository.selectOne(Fields.NAME.eq(project.getName())).getId();
    }

    @Override
    public Project queryById(Long id) {
        ProjectPoJo projectPoJo = projectRepository.selectById(id);
        if (projectPoJo == null) {
            return null;
        }
        Project project = Project.builder().build();
        ModelUtil.poJoToModel(projectPoJo, project);
        return project;
    }

    @Override
    public List<Project> queryAll() {
        List<ProjectPoJo> projectPoJos = projectRepository.selectAll();
        List<Project> projects = Lists.newLinkedList();
        List<Long> businessIds = Lists.newLinkedList();
        projectPoJos.forEach(project -> businessIds.add(project.getBusinessId()));
        List<Business> businesses = businessService.queryByIds(businessIds);
        Map<Long, Business> businessPojoMap = new HashMap<>();
        businesses.forEach(businessPojo -> {
            businessPojoMap.put(businessPojo.getId(), businessPojo);
        });
        projectPoJos.forEach(projectPoJo -> {
            Project project = Project.builder().build();
            ModelUtil.poJoToModel(projectPoJo, project);
            project.setBusiness(businessPojoMap.get(projectPoJo.getBusinessId()));
            projects.add(project);
        });
        return projects;
    }
}
