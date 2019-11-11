package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.business.BusinessPojo;
import com.bigdata.uno.common.model.project.Project;
import com.bigdata.uno.common.model.project.ProjectPojo;
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
        if (project.getId() != null) {
            projectRepository.updateNotNullFields(project);
            return project.getId();
        }
        projectRepository.insert(project);
        return projectRepository.selectOne(Fields.NAME.eq(project.getName())).getId();
    }

    @Override
    public Project queryById(Long id) {
        return projectRepository.selectById(id);
    }

    @Override
    public List<ProjectPojo> queryAll() {
        List<Project> projects = projectRepository.selectAll();
        List<ProjectPojo> projectPojos = Lists.newLinkedList();
        List<Long> businessIds = Lists.newLinkedList();
        projects.forEach(project -> businessIds.add(project.getBusinessId()));
        List<BusinessPojo> businessPojos = businessService.queryByIds(businessIds);
        Map<Long, BusinessPojo> businessPojoMap = new HashMap<>();
        businessPojos.forEach(businessPojo -> {
            businessPojoMap.put(businessPojo.getId(), businessPojo);
        });
        projects.forEach(project -> {
            ProjectPojo projectPojo = ProjectPojo.builder().build();
            ModelUtil.modelToPojo(project, projectPojo);
            projectPojo.setBusiness(businessPojoMap.get(project.getBusinessId()));
            projectPojos.add(projectPojo);
        });
        return projectPojos;
    }
}
