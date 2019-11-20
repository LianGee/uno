package com.bigdata.uno.api.controller;

import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.information.Info;
import com.bigdata.uno.common.model.requirement.Requirement;
import com.bigdata.uno.common.model.requirement.UpdateRequirement;
import com.bigdata.uno.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiMethod(value = "/requirement")
public class RequirementController extends BaseController {

    @Autowired
    private RequirementService requirementService;

    @ApiMethod(value = "/query/{id}", method = RequestMethod.GET)
    public Response queryById(@PathVariable Long id) {
        return Response.success(requirementService.queryById(id));
    }

    @ApiMethod(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody Requirement requirement) {
        return Response.success(requirementService.save(requirement, loginUtil.getLoginUser()));
    }

    @ApiMethod(value = "/query/all", method = RequestMethod.GET)
    public Response queryAll() {
        return Response.success(requirementService.queryAll());
    }

    @ApiMethod(value = "/query/projectId", method = RequestMethod.GET)
    public Response queryByProjectId(@RequestParam Long projectId) {
        return Response.success(requirementService.queryByProjectId(projectId));
    }

    @ApiMethod(value = "/statistic", method = RequestMethod.GET)
    public Response statistic(@RequestParam Long projectId) {
        return Response.success(requirementService.statistic(projectId));
    }

    @ApiMethod(value = "/status/flow", method = RequestMethod.POST)
    public Response statusFlow(@RequestBody UpdateRequirement updateRequirement) {
        return Response.success(requirementService.statusFlow(updateRequirement));
    }

    @ApiMethod(value = "/update/date", method = RequestMethod.POST)
    public Response updateDate(@RequestBody UpdateRequirement updateRequirement) {

        return Response.success(requirementService.updateDate(updateRequirement));
    }

    @ApiMethod(value = "/query/comments")
    public Response queryComments(@RequestParam Long id) {
        return Response.success(requirementService.queryComments(id));
    }

    @ApiMethod(value = "/add/comments")
    public Response addComments(@RequestBody Info info) {
        return Response.success(requirementService.addComment(info, loginUtil.getLoginUser()));
    }
}
