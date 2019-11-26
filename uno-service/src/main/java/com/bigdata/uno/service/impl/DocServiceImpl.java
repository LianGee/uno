package com.bigdata.uno.service.impl;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.common.doc.Doc;
import com.bigdata.uno.common.model.catalogue.Catalogue;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.service.CatalogueService;
import com.bigdata.uno.service.DocService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private CatalogueService catalogueService;

    @Value("${es.doc-index}")
    private String index;

    @Override
    public long save(Doc doc) {
        Preconditions.checkNotNull(doc.getTitle(), "标题不可为空");
        Long catalogueId = catalogueService.save(doc.getCatalogue());
        if (doc.getId() == null) {
            IndexRequest indexRequest = new IndexRequest(index);
            doc.setCatalogueId(catalogueId);
            indexRequest.source(JSON.toJSONString(doc), XContentType.JSON);
        } else {
            UpdateRequest updateRequest = new UpdateRequest(index, doc.getId().toString());
            updateRequest.doc(JSON.toJSONString(doc), XContentType.JSON);
        }
        return 0;
    }

    @Override
    public Doc queryById(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

}
