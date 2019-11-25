package com.bigdata.uno.service;

import com.bigdata.uno.common.model.catalogue.Catalogue;

import java.util.List;

public interface CatalogueService {
    List<Catalogue> queryByProjectId(Long projectId);

    boolean save(Catalogue catalogue);

    boolean delete(Long id);
}
