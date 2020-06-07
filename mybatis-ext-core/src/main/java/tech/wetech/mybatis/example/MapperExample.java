package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.mapper.BaseMapper;
import tech.wetech.mybatis.util.EntityMappingUtil;

/**
 * <p>Example条件查询，灵感由MyBatis Generator而来<p/>
 *
 * @author cjbi
 * @see Example
 */
public final class MapperExample<T> extends Example<T> {

    private final BaseMapper<T> mapper;

    @Override
    public MapperCriteria<T> or() {
        return (MapperCriteria<T>) super.or();
    }

    @Override
    public MapperCriteria<T> or(Criteria<T> criteria) {
        return (MapperCriteria<T>) super.or(criteria);
    }

    @Override
    public MapperCriteria<T> and() {
        return (MapperCriteria<T>) super.and();
    }

    @Override
    public MapperCriteria<T> and(Criteria<T> criteria) {
        return (MapperCriteria<T>) super.and(criteria);
    }

    public MapperExample(BaseMapper<T> mapper) {
        super(EntityMappingUtil.extractEntityClass(mapper.getClass().getInterfaces()[0]));
        this.mapper = mapper;
    }

    @Override
    public MapperCriteria<T> createCriteria() {
        return (MapperCriteria<T>) super.createCriteria();
    }

    @Override
    protected MapperCriteria createCriteriaInternal() {
        MapperCriteria mapperCriteria = new MapperCriteria<>(mapper, this);
        return mapperCriteria;
    }

    @Override
    public MapperExample<T> setSelects(Property<T, ?>... properties) {
        super.setSelects(properties);
        return this;
    }

    @Override
    public MapperExample<T> setSelects(String... properties) {
        super.setSelects(properties);
        return this;
    }

    @Override
    public MapperExample<T> setDistinct(boolean distinct) {
        super.setDistinct(distinct);
        return this;
    }

    @Override
    public MapperExample<T> setOrderByClause(String orderByClause) {
        super.setOrderByClause(orderByClause);
        return this;
    }

    @Override
    public MapperExample<T> setSort(Sort sort) {
        super.setSort(sort);
        return this;
    }


}
