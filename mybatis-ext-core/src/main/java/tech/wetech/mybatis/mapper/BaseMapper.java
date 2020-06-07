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
 * 基础语句SQL实体Mapper映射
 *
 * @author cjbi
 */
public interface BaseMapper<T> extends Mapper<T> {

    /**
     * 通过主键id删除一条记录
     * @param id 主键
     * @return 受影响的行数
     */
    @DeleteEntityProvider(type = BaseEntitySqlBuilder.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(Object id);

    /**
     * 插入记录
     * @param record 要插入的记录
     * @param <S> 实体类
     * @return 受影响的行数
     */
    @InsertEntityProvider(type = BaseEntitySqlBuilder.class, method = "insert")
    <S extends T> int insert(S record);

    /**
     * 插入非null的记录
     * @param record 要插入的记录
     * @param <S> 实体类
     * @return 受影响的行数
     */
    @InsertEntityProvider(type = BaseEntitySqlBuilder.class, method = "insertSelective")
    <S extends T> int insertSelective(S record);

    /**
     * 通过主键查询返回{@link java.util.Optional}包裹的记录
     * @param id 主键
     * @param <S> 实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByPrimaryKey")
    <S extends T> S selectByPrimaryKey(Object id);

    /**
     * 通过主键查询返回{@link java.util.Optional}包裹的记录
     * @param id 主键
     * @param <S> 实体类
     * @return 受影响的行数
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByPrimaryKeyWithOptional")
    <S extends T> Optional<S> selectByPrimaryKeyWithOptional(Object id);

    /**
     * 通过主键更新非null的记录
     * @param record 记录
     * @param <S> 实体类
     * @return 受影响的行数
     */
    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByPrimaryKey")
    <S extends T> int updateByPrimaryKey(S record);

    /**
     * 通过主键更新非null的记录
     * @param record 记录
     * @param <S> 实体类
     * @return 受影响的行数
     */
    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByPrimaryKeySelective")
    <S extends T> int updateByPrimaryKeySelective(S record);

    /**
     * 查询所有记录
     * @param <S> 实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectAll")
    <S extends T> List<S> selectAll();

    /**
     * 查询多条记录
     * @param record 条件
     * @param <S> 实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectList")
    <S extends T> List<S> selectList(S record);

    /**
     * 查询一条记录
     * @param record 条件
     * @param <S> 实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectOne")
    <S extends T> S selectOne(S record);

    /**
     * 查询一条记录通过{@link java.util.Optional}包裹
     * @param record 条件
     * @param <S> 实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectOneWithOptional")
    <S extends T> Optional<S> selectOneWithOptional(S record);

    /**
     * 通过主键判断记录是否存在
     * @param id 主键
     * @return 是否存在
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "existsByPrimaryKey")
    boolean existsByPrimaryKey(Object id);

    /**
     * 统计记录
     * @param record 条件
     * @param <S> 实体类
     * @return 记录数
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "count")
    <S extends T> int count(S record);

    /**
     * 通过{@link tech.wetech.mybatis.example.Example}查询记录
     *
     * @param example 条件查询
     * @param <S>     实体类
     * @return 返回的记录
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "selectByExample")
    <S extends T> List<S> selectByExample(Example<S> example);

    /**
     * 通过{@link tech.wetech.mybatis.example.Example}统计记录
     *
     * @param example 条件查询
     * @param <S>     实体类
     * @return 记录数
     */
    @SelectEntityProvider(type = BaseEntitySqlBuilder.class, method = "countByExample")
    <S extends T> int countByExample(Example<S> example);

    /**
     * 通过{@link tech.wetech.mybatis.example.Example}删除记录
     *
     * @param example 条件查询
     * @param <S>     实体类
     * @return 受影响的行数
     */
    @DeleteEntityProvider(type = BaseEntitySqlBuilder.class, method = "deleteByExample")
    <S extends T> int deleteByExample(Example<S> example);

    /**
     * 通过{@link tech.wetech.mybatis.example.Example}更新记录
     *
     * @param record  记录
     * @param example 条件
     * @param <S>     实体类
     * @return 受影响的行数
     */
    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByExample")
    <S extends T> int updateByExample(@Param("record") S record, @Param("example") Example<S> example);

    /**
     * 通过{@link tech.wetech.mybatis.example.Example}更新非null的记录
     *
     * @param record  记录
     * @param example 条件
     * @param <S>     实体类
     * @return 受影响的行数
     */
    @UpdateEntityProvider(type = BaseEntitySqlBuilder.class, method = "updateByExampleSelective")
    <S extends T> int updateByExampleSelective(@Param("record") S record, @Param("example") Example<S> example);

    /**
     * 创建Criteria条件
     *
     * @param <S> 实体类
     * @return Criteria条件
     */
    default <S extends T> MapperCriteria<S> createCriteria() {
        return new MapperCriteria(this);
    }

    /**
     * 创建Example条件
     *
     * @param <S>
     * @return Example条件
     */
    default <S extends T> MapperExample<S> createExample() {
        return new MapperExample(this);
    }

}
