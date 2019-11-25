package com.bigdata.uno.common.model.catalogue;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CataloguePoJo extends BaseEntity<CataloguePoJo, Long> {
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_leaf")
    private boolean isLeaf;
}
