package com.bigdata.uno.common.model.information;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info extends BaseEntity<Info, Long> {
    private User user;

    private Long requirementId;

    @JSONColumn
    private String recipient;

    private String content;
}
