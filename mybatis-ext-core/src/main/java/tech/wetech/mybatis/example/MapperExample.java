package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.mapper.BaseMapper;
import tech.wetech.mybatis.util.EntityMappingUtil;

/**
 * @author cjbi
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

    /**
     * 请使用 {@link MapperExample#setSelects(tech.wetech.mybatis.domain.Property[])} 替代
     *
     * @param properties
     * @return
     */
    @Override
    @Deprecated
    public MapperExample<T> setColumns(Property<T, ?>... properties) {
        super.setColumns(properties);
        return this;
    }

    /**
     * 请使用 {@link MapperExample#setSelects(java.lang.String...)}方法替代
     *
     * @param columns
     * @return
     */
    @Override
    @Deprecated
    public Example<T> setColumns(String... columns) {
        return super.setColumns(columns);
    }

    @Override
    public Example<T> setSelects(Property<T, ?>... properties) {
        return super.setSelects(properties);
    }

    @Override
    public Example<T> setSelects(String... columns) {
        return super.setSelects(columns);
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
    public MapperExample<T> setPage(int pageSize, int pageNumber) {
        super.setPage(pageSize, pageNumber);
        return this;
    }

    @Override
    public MapperExample<T> setSort(Sort sort) {
        super.setSort(sort);
        return this;
    }


}
