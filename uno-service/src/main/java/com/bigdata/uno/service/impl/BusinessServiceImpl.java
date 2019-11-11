package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.BusinessRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public Long save(Business business) {
        Preconditions.checkNotNull(business.getName(), "业务线名称不可为空");
        if (business.getId() != null) { //update
            businessRepository.updateNotNullFields(business);
            return business.getId();
        }
        businessRepository.insert(business);
        return businessRepository.selectOne(Fields.NAME.eq(business.getName())).getId();
    }

    @Override
    public Business queryById(Long id) {
        return businessRepository.selectOne(Fields.ID.eq(id));
    }
}
