package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.business.BusinessPoJo;
import com.bigdata.uno.common.model.business.Business;
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
    public Long save(BusinessPoJo businessPoJo) {
        Preconditions.checkNotNull(businessPoJo.getName(), "业务线名称不可为空");
        if (businessPoJo.getId() != null) { //update
            businessRepository.updateNotNullFields(businessPoJo);
            return businessPoJo.getId();
        }
        businessRepository.insert(businessPoJo);
        return businessRepository.selectOne(Fields.NAME.eq(businessPoJo.getName())).getId();
    }

    @Override
    public Business queryById(Long id) {
        BusinessPoJo businessPoJo = businessRepository.selectOne(Fields.ID.eq(id));
        Business business = Business.builder().build();
        ModelUtil.poJoToModel(businessPoJo, business);
        return business;
    }

    @Override
    public List<Business> queryAll() {
        List<BusinessPoJo> businessPoJos = businessRepository.selectAll();
        return getBusinessPojos(businessPoJos);
    }

    @Override
    public List<Business> queryByIds(List<Long> ids) {
        List<BusinessPoJo> businessPoJos = businessRepository.selectWhere(Fields.ID.in(ids));
        return getBusinessPojos(businessPoJos);
    }

    private List<Business> getBusinessPojos(List<BusinessPoJo> businessPoJos) {
        List<Business> businesses = Lists.newLinkedList();
        businessPoJos.forEach(business -> {
            Business businessPojo1 = Business.builder().build();
            ModelUtil.poJoToModel(business, businessPojo1);
            businesses.add(businessPojo1);
        });
        return businesses;
    }
}
