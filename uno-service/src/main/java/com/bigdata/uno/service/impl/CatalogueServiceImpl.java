package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.catalogue.Catalogue;
import com.bigdata.uno.common.model.catalogue.CataloguePoJo;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.CatalogueRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.CatalogueService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogueServiceImpl implements CatalogueService {
    @Autowired
    private CatalogueRepository catalogueRepository;

    public void buildCatalogue(Catalogue catalogue, Map<Long, List<Long>> catalogueMap, Map<Long, CataloguePoJo> cataloguePoJoMap) {
        if (catalogueMap.containsKey(catalogue.getId())) {
            catalogue.setChildren(Lists.newLinkedList());
            catalogueMap.get(catalogue.getId()).forEach(childId -> {
                CataloguePoJo cataloguePoJo = cataloguePoJoMap.get(childId);
                Catalogue child = Catalogue.builder().children(Lists.newLinkedList()).build();
                ModelUtil.poJoToModel(cataloguePoJo, child);
                if (catalogueMap.containsKey(childId)) {
                    buildCatalogue(child, catalogueMap, cataloguePoJoMap);
                }
                catalogue.getChildren().add(child);
            });
        } else {
            catalogue.setChildren(Lists.newLinkedList());
        }
    }

    @Override
    public List<Catalogue> queryByProjectId(Long projectId) {
        List<CataloguePoJo> cataloguePoJos = catalogueRepository.selectWhere(Fields.PROJECT_ID.eq(projectId));
        List<Catalogue> catalogues = Lists.newLinkedList();
        Map<Long, List<Long>> catalogueMap = new HashMap<>();
        Map<Long, CataloguePoJo> cataloguePoJoMap = new HashMap<>();
        cataloguePoJos.forEach(cataloguePoJo -> {
            if (!catalogueMap.containsKey(cataloguePoJo.getParentId())) {
                catalogueMap.put(cataloguePoJo.getParentId(), Lists.newLinkedList());
            }
            catalogueMap.get(cataloguePoJo.getParentId()).add(cataloguePoJo.getId());
            cataloguePoJoMap.put(cataloguePoJo.getId(), cataloguePoJo);
        });
        catalogueMap.forEach((parentId, children) -> {
            if (parentId.equals(0L)) {
                children.forEach(childId -> {
                    CataloguePoJo cataloguePoJo = cataloguePoJoMap.get(childId);
                    Catalogue catalogue = Catalogue.builder().build();
                    ModelUtil.poJoToModel(cataloguePoJo, catalogue);
                    buildCatalogue(catalogue, catalogueMap, cataloguePoJoMap);
                    catalogues.add(catalogue);
                });
            }
        });
        return catalogues;
    }

    @Override
    public Long save(Catalogue catalogue) {
        Preconditions.checkNotNull(catalogue.getTitle(), "目录名不可为空");
        Preconditions.checkNotNull(catalogue.getProjectId(), "项目Id不可为空");
        CataloguePoJo cataloguePoJo = CataloguePoJo.builder().build();
        ModelUtil.modelToPoJO(catalogue, cataloguePoJo);
        if (catalogue.getId() != null) {
            catalogueRepository.updateNotNullFields(cataloguePoJo);
            return catalogue.getId();
        }
        Preconditions.checkNull(catalogueRepository.selectOne(
                Fields.PROJECT_ID.eq(catalogue.getProjectId())
                        .and(Fields.TITLE.eq(catalogue.getTitle()))
                        .and(Fields.PARENT_ID.eq(catalogue.getParentId()))
        ), String.format("目录已存在，请修改目录名：%s", catalogue.getTitle()));
        catalogueRepository.insert(cataloguePoJo);
        CataloguePoJo curr = catalogueRepository.selectOne(
                Fields.PROJECT_ID.eq(catalogue.getProjectId())
                        .and(Fields.TITLE.eq(catalogue.getTitle()))
                        .and(Fields.PARENT_ID.eq(catalogue.getParentId()))
        );
        return curr.getId();
    }

    @Override
    public boolean delete(Long id) {
        catalogueRepository.delete(id);
        return true;
    }
}
