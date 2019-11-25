package com.bigdata.uno.service.impl;

import com.bigdata.uno.service.EsTestService;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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

    @Override
    public Object get(String index, String type, Long id) {
        GetRequest request = new GetRequest(index, type, id.toString());
        try {
            return client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object search(String index, String type, String name) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.should(QueryBuilders.matchQuery("firstname", name));
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(boolBuilder);
		sourceBuilder.from(0);
		sourceBuilder.size(100); // 获取记录数，默认10
		//sourceBuilder.fetchSource(new String[] {}, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.types(type);
		searchRequest.source(sourceBuilder);
//        SearchResponse response = null;
        try {
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
//		SearchHits hits = response.getHits();
//		SearchHit[] searchHits = hits.getHits();
//		for (SearchHit hit : searchHits) {
//			System.out.println("search -> " + hit.getSourceAsString());
//		}
        return null;
    }
}
