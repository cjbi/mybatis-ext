package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public class Example<T> implements Serializable {

    protected List<String> columns;
    protected boolean distinct;
    protected String orderByClause;
    protected final Class<?> entityClass;
    protected List<Criteria> oredCriteria;

    public Criteria<T> or() {
        Criteria<T> criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria<T> or(Criteria<T> criteria) {
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria<T> and() {
        Criteria<T> criteria = createCriteriaInternal();
        criteria.andOr("and");
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria<T> and(Criteria<T> criteria) {
        oredCriteria.add(criteria);
        criteria.andOr("and");
        return criteria;
    }

    public static <T> Example<T> of(Class<T> entityClass) {
        return new Example<>(entityClass);
    }

    public Criteria<T> createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria<T> createCriteriaInternal() {
        Criteria<T> criteria = new Criteria<>();
        return criteria;
    }

    public Example(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.oredCriteria = new ArrayList<>();
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public List<String> getColumns() {
        return columns;
    }

    public Example<T> setSelects(Property<T, ?>... properties) {
        this.columns = Arrays.stream(properties)
                .map(p -> p.getColumnName(entityClass.getName()))
                .collect(Collectors.toList());
        return this;
    }

    public Example<T> setSelects(String... properties) {
        this.columns = Arrays.asList(properties);
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Example<T> setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Example<T> setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public Example<T> setSort(Sort sort) {
        if (sort.getOrders() != null && sort.getOrders().size() > 0) {
            this.orderByClause = sort.getOrders().stream()
                    .map(order -> EntityMappingUtil.getColumnName(entityClass.getName(), order.getProperty()).concat(" ").concat(order.getDirection().toString()))
                    .collect(Collectors.joining(","));
        }
        return this;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Override
    public String toString() {
        return "Example{" +
                "columns=" + columns +
                ", distinct=" + distinct +
                ", orderByClause='" + orderByClause + '\'' +
                ", oredCriteria=" + oredCriteria +
                ", entityClass=" + entityClass +
                '}';
    }

}
