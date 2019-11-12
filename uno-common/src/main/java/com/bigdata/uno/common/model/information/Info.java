package com.bigdata.uno.common.model.information;

import com.bigdata.uno.common.model.JSONColumn;
import com.bigdata.uno.common.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info extends BaseEntity<Info, Long> {
    @JSONColumn
    private String recipient;

    private String content;
}
