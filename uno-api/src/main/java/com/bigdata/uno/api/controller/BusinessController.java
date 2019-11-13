package com.bigdata.uno.api.controller;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.business.BusinessPoJo;
import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody Business business) {
        BusinessPoJo businessPoJo = BusinessPoJo.builder().build();
        ModelUtil.modelToPoJO(business, businessPoJo);
        return Response.success(businessService.save(businessPoJo));
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
