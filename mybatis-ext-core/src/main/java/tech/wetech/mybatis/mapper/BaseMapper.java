package tech.wetech.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import tech.wetech.mybatis.annotation.DeleteEntityProvider;
import tech.wetech.mybatis.annotation.InsertEntityProvider;
import tech.wetech.mybatis.annotation.SelectEntityProvider;
import tech.wetech.mybatis.annotation.UpdateEntityProvider;
import tech.wetech.mybatis.example.Example;
import tech.wetech.mybatis.example.MapperCriteria;
import tech.wetech.mybatis.example.MapperExample;

import java.util.List;
import java.util.Optional;

/**
 * @author cjbi
 */
public interface BaseMapper<T> extends Mapper<T> {

    @DeleteEntityProvider(type = BaseEntitySqlBuilder.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(Object id);

    @InsertEntityProvider(type = BaseEntitySqlBuilder.class, method = "insert")
    <S extends T> int insert(S record);

    @InsertEntityProvider(type = BaseEntitySqlBuilder.class, method = "insertSelective")
    <S extends T> int insertSelective(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByPrimaryKey")
    <S extends T> S selectByPrimaryKey(Object id);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByPrimaryKeyWithOptional")
    <S extends T> Optional<S> selectByPrimaryKeyWithOptional(Object id);

    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByPrimaryKey")
    <S extends T> int updateByPrimaryKey(S record);

    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByPrimaryKeySelective")
    <S extends T> int updateByPrimaryKeySelective(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectAll")
    <S extends T> List<S> selectAll();

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectList")
    <S extends T> List<S> selectList(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectOne")
    <S extends T> S selectOne(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectOneWithOptional")
    <S extends T> Optional<S> selectOneWithOptional(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "existsByPrimaryKey")
    boolean existsByPrimaryKey(Object id);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "count")
    <S extends T> int count(S record);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByExample")
    <S extends T> List<S> selectByExample(Example<S> example);

    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "countByExample")
    <S extends T> int countByExample(Example<S> example);

    @DeleteEntityProvider(type = BaseEntitySqlBuilder.class, method = "deleteByExample")
    <S extends T> int deleteByExample(Example<S> example);

    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByExample")
    <S extends T> int updateByExample(@Param("record") S record, @Param("example") Example<S> example);

    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByExampleSelective")
    <S extends T> int updateByExampleSelective(@Param("record") S record, @Param("example") Example<S> example);

    default <S extends T> MapperCriteria<S> createCriteria() {
        return new MapperCriteria(this);
    }

    default <S extends T> MapperExample<S> createExample() {
        return new MapperExample(this);
    }

}
