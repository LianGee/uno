package com.bigdata.uno.service;

public interface EsTestService {
    boolean existIndex(String index);

    Object get(String index, String type, Long id);

    Object search(String index, String type, String name);
}
