package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.mapper.BaseMapper;
import tech.wetech.mybatis.util.EntityMappingUtil;
/**
 * @author cjbi
 */
public final class MapperExample<T> extends Example<T> {

    private final BaseMapper<T> mapper;

    public MapperExample(BaseMapper<T> mapper) {
        super(EntityMappingUtil.extractEntityClass(mapper.getClass().getInterfaces()[0]));
        this.mapper = mapper;
    }

    @Override
    public MapperCriteria<T> createCriteria() {
        return (MapperCriteria<T>) super.createCriteria(new MapperCriteria<>(mapper, this));
    }

    @Override
    public MapperExample<T> setColumns(Property<T, ?>... properties) {
        super.setColumns(properties);
        return this;
    }

    @Override
    public Example<T> setColumns(String... columns) {
        return super.setColumns(columns);
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
    public MapperExample<T> setLimit(int limit) {
        super.setLimit(limit);
        return this;
    }

    @Override
    public MapperExample<T> setOffset(int offset) {
        super.setOffset(offset);
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
