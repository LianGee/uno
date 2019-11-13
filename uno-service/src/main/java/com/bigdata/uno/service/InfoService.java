package com.bigdata.uno.service;

import com.bigdata.uno.common.model.information.Info;

import java.util.List;

public interface InfoService {
    boolean inform(String creator, List<String> recipients, String title, String content);

    List<Info> queryInfo(Long requirementId);
}
