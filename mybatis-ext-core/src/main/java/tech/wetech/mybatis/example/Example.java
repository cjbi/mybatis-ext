package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public class Example<T> implements Serializable {

    protected String[] columns;
    protected boolean distinct;
    protected boolean forUpdate;
    protected String orderByClause;
    protected int limit;
    protected int offset;
    protected Criteria<T> criteria;
    protected final Class<?> entityClass;

    public static <T> Example<T> of(Class<T> entityClass) {
        return new Example<>(entityClass);
    }

    public Criteria<T> createCriteria() {
        this.criteria = new Criteria<>();
        return this.criteria;
    }

    public Criteria<T> createCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this.criteria;
    }

    public Example(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Criteria<T> getCriteria() {
        return criteria != null ? criteria : createCriteria();
    }

    public void clear() {
        orderByClause = null;
        limit = 0;
        offset = 0;
        distinct = false;
        criteria.clear();
    }

    public String[] getColumns() {
        return columns;
    }

    @Deprecated
    public Example<T> setColumns(Property<T, ?>... properties) {
        setSelects(properties);
        return this;
    }

    @Deprecated
    public Example<T> setColumns(String... columns) {
        setSelects(columns);
        return this;
    }

    public Example<T> setSelects(Property<T, ?>... properties) {
        this.columns = Arrays.stream(properties)
                .map(p -> p.getColumnName(entityClass.getName()))
                .collect(Collectors.toList())
                .toArray(new String[properties.length]);
        return this;
    }

    public Example<T> setSelects(String... columns) {
        this.columns = columns;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Example<T> setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public Example<T> setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public int getLimit() {
        return limit;
    }

    public Example<T> setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public Example<T> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public Example<T> setPage(int pageSize, int pageNumber) {
        this.limit = pageSize;
        this.offset = (pageNumber - 1) * pageSize;
        return this;
    }

    public Example<T> setSort(Sort sort) {
        if (sort.getOrders() != null && sort.getOrders().size() > 0) {
            this.orderByClause = sort.getOrders().stream()
                    .map(order -> EntityMappingUtil.getColumnName(entityClass.getName(), order.getProperty()).concat(" ").concat(order.getDirection().toString()))
                    .collect(Collectors.joining(","));
        }
        return this;
    }

    @Override
    public String toString() {
        return "Example{" +
                "columns=" + Arrays.toString(columns) +
                ", distinct=" + distinct +
                ", orderByClause='" + orderByClause + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                ", criteria=" + criteria +
                ", entityClass=" + entityClass +
                '}';
    }

}
