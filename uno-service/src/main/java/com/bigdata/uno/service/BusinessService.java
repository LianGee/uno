package com.bigdata.uno.service;

import com.bigdata.uno.common.model.business.Business;

public interface BusinessService {
    Long save(Business business);

    Business queryById(Long id);
}
