package com.bigdata.uno.service.impl;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.common.doc.Doc;
import com.bigdata.uno.common.model.catalogue.Catalogue;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.service.CatalogueService;
import com.bigdata.uno.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
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
        if (doc.getCatalogueId() == null || doc.getCatalogueId() == 0) {
            doc.setCatalogue(Catalogue.builder()
                    .title(doc.getTitle())
                    .projectId(doc.getProjectId())
                    .parentId(0L)
                    .build());
        }
        Long catalogueId = catalogueService.save(doc.getCatalogue());
        if (doc.getCreatedAt() == null) {
            doc.setCreatedAt(System.currentTimeMillis() / 1000);
        }
        if (doc.getUpdatedAt() == null) {
            doc.setUpdatedAt(System.currentTimeMillis() / 1000);
        }
        doc.setIsDelete(false);
        if (doc.getId() == null || doc.getId() == 0) {
            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.id(catalogueId.toString());
            doc.setCatalogueId(catalogueId);
            indexRequest.source(JSON.toJSONString(doc), XContentType.JSON);
            try {
                IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
                log.info(JSON.toJSONString(indexResponse));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UpdateRequest updateRequest = new UpdateRequest(index, doc.getId().toString());
            doc.setUpdatedAt(System.currentTimeMillis() / 1000);
            updateRequest.doc(JSON.toJSONString(doc), XContentType.JSON);
            try {
                UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
                log.info(JSON.toJSONString(updateResponse));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return catalogueId;
    }

    @Override
    public Doc queryById(long id) {
        GetRequest getRequest = new GetRequest(index, String.valueOf(id));
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            log.info(JSON.toJSONString(getResponse));
            return JSON.parseObject(JSON.toJSONString(getResponse.getSource()), Doc.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

}
