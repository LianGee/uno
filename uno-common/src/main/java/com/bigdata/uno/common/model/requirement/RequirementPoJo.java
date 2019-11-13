package com.bigdata.uno.common.model.requirement;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RequirementPoJo extends BaseEntity<RequirementPoJo, Long> {
    @Column(name = "title")
    private String title;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "priority")
    private int priority;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    @Column(name = "content")
    private String content;

    @Column(name = "assign_to")
    private String assignTo;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @Column(name = "creator")
    private String creator;
}
