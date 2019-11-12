package com.bigdata.uno.common.model.information;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InfoPoJo extends BaseEntity<InfoPoJo, Long> {
    @Column(name = "recipient")
    private String recipient;

    @Column(name = "content")
    private String content;
}
