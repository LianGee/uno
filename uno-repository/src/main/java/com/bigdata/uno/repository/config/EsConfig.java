package com.bigdata.uno.repository.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class EsConfig {
    @Value("${es.hosts}")
    private String hosts;

    @Value("${es.port}")
    private int port;

    @Value("${es.schema}")
    private String schema;

    @Value("${es.connect-time-out}")
    private int connectTimeOut;

    @Value("${es.socket-time-out}")
    private int socketTimeOut;

    @Value("${es.socket-time-out}")
    private int connectionRequestTimeOut = 500; // 获取连接的超时时间

    @Value("${es.max-connect-num}")
    private int maxConnectNum = 100; // 最大连接数

    @Value("${es.max-connect-per-route}")
    private int maxConnectPerRoute = 100; // 最大路由连接数

    private ArrayList<HttpHost> hostList = new ArrayList<>();


	@Bean
	public RestHighLevelClient client() {
		String[] hostStrs = hosts.split(",");
		for (String host : hostStrs) {
			hostList.add(new HttpHost(host, port, schema));
		}
		RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
		// 异步httpclient连接延时配置
		builder.setRequestConfigCallback(requestConfigBuilder -> {
			requestConfigBuilder.setConnectTimeout(connectTimeOut);
			requestConfigBuilder.setSocketTimeout(socketTimeOut);
			requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
			return requestConfigBuilder;
		});
		// 异步httpclient连接数配置
		builder.setHttpClientConfigCallback(httpClientBuilder -> {
			httpClientBuilder.setMaxConnTotal(maxConnectNum);
			httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
			return httpClientBuilder;
		});
		return new RestHighLevelClient(builder);
	}
}
