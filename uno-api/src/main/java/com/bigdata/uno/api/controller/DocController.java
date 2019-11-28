package com.bigdata.uno.api.controller;

import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.doc.Doc;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.catalogue.Catalogue;
import com.bigdata.uno.service.CatalogueService;
import com.bigdata.uno.service.DocService;
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


    @Autowired
    private DocService docService;

    @ApiMethod(value = "/save", method = RequestMethod.POST, requireLogin = false)
    public Response save(@RequestBody Doc doc) {
        return Response.success(docService.save(doc));
    }

    @ApiMethod(value = "/catalogue/query/project/{id}", method = RequestMethod.GET)
    public Response queryCatalogueByProjectId(@PathVariable Long id) {
        return Response.success(catalogueService.queryByProjectId(id));
    }

    @ApiMethod(value = "/get/{id}", method = RequestMethod.GET, requireLogin = false)
    public Response queryDoc(@PathVariable Long id) {
        return Response.success(docService.queryById(id));
    }

}
