package com.bigdata.uno.common.model.project;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectPoJo extends BaseEntity<ProjectPoJo, Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "owner")
    private String owner;

    @Column(name = "level")
    private int level;

    @Column(name = "type")
    private int type;

    @Column(name = "language")
    private String language;

    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "backend_host")
    private String backendHost;

    @Column(name = "frontend_host")
    private String frontendHost;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;
}
