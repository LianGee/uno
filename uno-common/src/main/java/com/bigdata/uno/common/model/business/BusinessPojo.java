package com.bigdata.uno.common.model.business;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessPojo extends BaseEntity<BusinessPojo, Long> {
    private String name;
    private String chineseName;
    @JSONColumn
    private List<String> owner;
}
