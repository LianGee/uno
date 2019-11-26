package com.bigdata.uno.common.doc;

import com.bigdata.uno.common.model.catalogue.Catalogue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doc {
    private Long id;

    private String content;

    private String title;

    private Long projectId;

    private Long catalogueId;

    private Catalogue catalogue;

    private Long createdAt;

    private Long updatedAt;

    private Boolean isDelete;
}
