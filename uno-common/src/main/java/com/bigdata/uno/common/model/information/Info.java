package com.bigdata.uno.common.model.information;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.user.User;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Info extends BaseEntity<Info, Long> {
    private User user;

    private Long requirementId;

    @JSONColumn
    private List<String> recipient;

    private String content;
}
