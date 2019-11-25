package com.bigdata.uno.service.impl;

import com.bigdata.uno.service.EsTestService;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsServiceImpl implements EsTestService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean existIndex(String index) {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
