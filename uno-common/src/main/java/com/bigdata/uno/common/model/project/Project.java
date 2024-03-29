package com.bigdata.uno.common.model.project;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.business.Business;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity<Project, Long> {
    private String name;

    @JSONColumn
    private List<String> owner;

    private int level;

    private int type;

    @JSONColumn
    private List<String> language;

    private Long businessId;

    private String backendHost;

    private String frontendHost;

    private String description;

    private String logo;

    private Business business;
}
