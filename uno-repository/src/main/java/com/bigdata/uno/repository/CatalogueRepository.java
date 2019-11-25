package com.bigdata.uno.repository;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.jooq.QueryPart;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.bigdata.uno.common.model.catalogue.CataloguePoJo;
import com.bigdata.uno.repository.base.ListOption;
import com.bigdata.uno.repository.base.AbstractSqlProvider;
import com.bigdata.uno.repository.base.BaseRepository;
import com.bigdata.uno.repository.base.RepositoryGenerator;

/**
 * CataloguePoJoRepository generated by {@link RepositoryGenerator}
 *
 * @author bchen
 */
@Repository
public interface CatalogueRepository extends BaseRepository<CataloguePoJo> {

    class SqlProvider extends AbstractSqlProvider {
        @Override
        public String table() {
            return "catalogue";
        }
    }

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectById")
    CataloguePoJo selectById(Long id);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectOne")
    CataloguePoJo selectOne(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectWhere")
    List<CataloguePoJo> selectWhere(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectAll")
    List<CataloguePoJo> selectAll();

    @Override
    @InsertProvider(type = SqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(CataloguePoJo entity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "updateByFields")
    int updateByFields(@Param("old") CataloguePoJo oldEntity,
                       @Param("new") CataloguePoJo newEntity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "updateNotNullFields")
    int updateNotNullFields(CataloguePoJo entity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "softDelete")
    int delete(Long id);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "softDeleteWhere")
    int deleteWhere(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectPage")
    Page<CataloguePoJo> selectPage(@Param("option") ListOption option,
                               @Param("pageNumKey") Long pageNum,
                               @Param("pageSizeKey") Long pageSize);

}