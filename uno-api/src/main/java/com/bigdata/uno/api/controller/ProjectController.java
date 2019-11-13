package com.bigdata.uno.api.controller;


import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.project.Project;
import com.bigdata.uno.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Response queryById(@PathVariable Long id) {
        return Response.success(projectService.queryById(id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody Project project) {
        return Response.success(projectService.save(project));
    }

    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    public Response queryAll() {
        return Response.success(projectService.queryAll());
    }
}
