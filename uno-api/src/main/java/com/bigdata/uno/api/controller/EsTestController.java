package com.bigdata.uno.api.controller;


import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.service.EsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMethod(value = "/es")
public class EsTestController {

    @Autowired
    private EsTestService esTestService;

    @ApiMethod(value = "/index")
    public Response existIndex(@RequestParam String index) {
        return Response.success(esTestService.existIndex(index));
    }
}
