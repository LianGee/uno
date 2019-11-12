package com.bigdata.uno.service.impl;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.common.model.information.InfoPoJo;
import com.bigdata.uno.repository.InfoRepository;
import com.bigdata.uno.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoRepository infoRepository;

    @Override
    public boolean inform(List<String> recipients, String title, String content) {
        InfoPoJo infoPoJo = InfoPoJo.builder()
                .recipient(JSON.toJSONString(recipients))
                .content(content)
                .build();
        return infoRepository.insert(infoPoJo) > 0;
    }
}
