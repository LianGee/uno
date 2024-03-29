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
import com.bigdata.uno.common.model.requirement.RequirementPoJo;
import com.bigdata.uno.repository.base.ListOption;
import com.bigdata.uno.repository.base.AbstractSqlProvider;
import com.bigdata.uno.repository.base.BaseRepository;
import com.bigdata.uno.repository.base.RepositoryGenerator;

/**
 * RequirementRepository generated by {@link RepositoryGenerator}
 *
 * @author bchen
 */
@Repository
public interface RequirementRepository extends BaseRepository<RequirementPoJo> {

    class SqlProvider extends AbstractSqlProvider {
        @Override
        public String table() {
            return "requirement";
        }
    }

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectById")
    RequirementPoJo selectById(Long id);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectOne")
    RequirementPoJo selectOne(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectWhere")
    List<RequirementPoJo> selectWhere(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectAll")
    List<RequirementPoJo> selectAll();

    @Override
    @InsertProvider(type = SqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(RequirementPoJo entity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "updateByFields")
    int updateByFields(@Param("old") RequirementPoJo oldEntity,
                       @Param("new") RequirementPoJo newEntity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "updateNotNullFields")
    int updateNotNullFields(RequirementPoJo entity);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "softDelete")
    int delete(Long id);

    @Override
    @UpdateProvider(type = SqlProvider.class, method = "softDeleteWhere")
    int deleteWhere(QueryPart where);

    @Override
    @SelectProvider(type = SqlProvider.class, method = "selectPage")
    Page<RequirementPoJo> selectPage(@Param("option") ListOption option,
                               @Param("pageNumKey") Long pageNum,
                               @Param("pageSizeKey") Long pageSize);

}
