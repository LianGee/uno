package com.bigdata.uno.api.controller;


import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.service.EsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMethod(value = "/es")
public class EsTestController {

    @Autowired
    private EsTestService esTestService;

    @ApiMethod(value = "/index", method = RequestMethod.GET, requireLogin = false)
    public Response existIndex(@RequestParam String index) {
        return Response.success(esTestService.existIndex(index));
    }

    @ApiMethod(value = "/get", method = RequestMethod.GET, requireLogin = false)
    public Response get(@RequestParam String index, @RequestParam String type, @RequestParam Long id) {
        return Response.success(esTestService.get(index, type, id));
    }

    @ApiMethod(value = "/search", method = RequestMethod.GET, requireLogin = false)
    public Response search(@RequestParam String index, @RequestParam String type, @RequestParam String name) {
        return Response.success(esTestService.search(index, type, name));
    }
}
