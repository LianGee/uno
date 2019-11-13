package com.bigdata.uno.common.model.business;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessPoJo extends BaseEntity<BusinessPoJo, Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "chinese_name")
    private String chineseName;

    @Column(name = "owner")
    private String  owner;
}
