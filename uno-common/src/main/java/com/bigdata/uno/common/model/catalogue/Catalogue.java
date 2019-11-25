package com.bigdata.uno.common.model.catalogue;

import com.bigdata.uno.common.model.base.BaseEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Catalogue extends BaseEntity<CataloguePoJo, Long> {
    private Long projectId;

    private String title;

    private Long parentId;

    private List<Catalogue> children;
}
