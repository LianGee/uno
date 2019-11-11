package com.bigdata.uno.api.controller;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.common.model.business.BusinessPojo;
import com.bigdata.uno.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody BusinessPojo businessPojo) {
        Business business = Business.builder().build();
        ModelUtil.pojoToModel(businessPojo, business);
        return Response.success(businessService.save(business));
    }

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Response queryById(@PathVariable Long id) {
        return Response.success(businessService.queryById(id));
    }

    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    public Response queryAll() {
        return Response.success(businessService.queryAll());
    }
}
