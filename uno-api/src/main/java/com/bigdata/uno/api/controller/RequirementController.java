package com.bigdata.uno.api.controller;

import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.requirement.Requirement;
import com.bigdata.uno.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/requirement")
public class RequirementController {

    @Autowired
    private RequirementService requirementService;

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Response queryById(@PathVariable Long id) {
        return Response.success(requirementService.queryById(id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody Requirement requirement) {
        return Response.success(requirementService.save(requirement));
    }

    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    public Response queryAll() {
        return Response.success(requirementService.queryAll());
    }

    @RequestMapping(value = "/query/projectId", method = RequestMethod.GET)
    public Response queryByProjectId(@RequestParam Long projectId) {
        return Response.success(requirementService.queryByProjectId(projectId));
    }

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public Response statistic(@RequestParam Long projectId) {
        return Response.success(requirementService.statistic(projectId));
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response status(@RequestParam int status) {
        return Response.success(null);
    }
}
