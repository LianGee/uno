package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.business.Business;
import com.bigdata.uno.common.model.business.BusinessPojo;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.BusinessRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.BusinessService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public BusinessPojo queryById(Long id) {
        Business business = businessRepository.selectOne(Fields.ID.eq(id));
        BusinessPojo businessPojo = BusinessPojo.builder().build();
        ModelUtil.modelToPojo(business, businessPojo);
        return businessPojo;
    }

    @Override
    public List<BusinessPojo> queryAll() {
        List<Business> businesses = businessRepository.selectAll();
        return getBusinessPojos(businesses);
    }

    @Override
    public List<BusinessPojo> queryByIds(List<Long> ids) {
        List<Business> businesses = businessRepository.selectWhere(Fields.ID.in(ids));
        return getBusinessPojos(businesses);
    }

    private List<BusinessPojo> getBusinessPojos(List<Business> businesses) {
        List<BusinessPojo> businessPojos = Lists.newLinkedList();
        businesses.forEach(business -> {
            BusinessPojo businessPojo = BusinessPojo.builder().build();
            ModelUtil.modelToPojo(business, businessPojo);
            businessPojos.add(businessPojo);
        });
        return businessPojos;
    }
}
