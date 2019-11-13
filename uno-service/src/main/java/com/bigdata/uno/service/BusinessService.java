package com.bigdata.uno.service;

import com.bigdata.uno.common.model.business.BusinessPoJo;
import com.bigdata.uno.common.model.business.Business;

import java.util.List;

public interface BusinessService {
    Long save(BusinessPoJo businessPoJo);

    Business queryById(Long id);

    List<Business> queryAll();

    List<Business> queryByIds(List<Long> ids);
}
