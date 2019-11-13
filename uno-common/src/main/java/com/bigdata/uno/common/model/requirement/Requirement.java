package com.bigdata.uno.common.model.requirement;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Requirement extends BaseEntity<Requirement, Long> {
    private String title;

    private Long projectId;

    private int priority;

    private int type;

    private int status;

    private String content;

    @JSONColumn
    private List<String> assignTo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    private String creator;
}
