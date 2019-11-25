package com.bigdata.uno.api.controller;

import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.catalogue.Catalogue;
import com.bigdata.uno.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMethod(value = "/doc")
public class DocController {

    @Autowired
    private CatalogueService catalogueService;

    @ApiMethod(value = "/save", method = RequestMethod.POST, requireLogin = false)
    public Response save(@RequestBody Catalogue catalogue) {
        return Response.success(catalogueService.save(catalogue));
    }

    @ApiMethod(value = "/query/project/{id}", method = RequestMethod.GET, requireLogin = false)
    public Response queryByProjectId(@PathVariable Long id) {
        return Response.success(catalogueService.queryByProjectId(id));
    }
}
