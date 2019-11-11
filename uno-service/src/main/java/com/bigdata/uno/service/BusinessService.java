package com.bigdata.uno.service;

import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.common.model.business.BusinessPojo;

import java.util.List;

public interface BusinessService {
    Long save(Business business);

    BusinessPojo queryById(Long id);

    List<BusinessPojo> queryAll();

    List<BusinessPojo> queryByIds(List<Long> ids);
}
