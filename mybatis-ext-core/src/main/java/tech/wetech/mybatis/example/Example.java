package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;
import tech.wetech.mybatis.domain.Sort;
import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Example条件查询，灵感由MyBatis Generator而来</p>
 * <b>使用示例:</b>
 * <pre>
 *  // 一、简单的条件查询：
 *  Example&lt;User&gt; example = Example.of(User.class);
 *  example.createCriteria()
 *            .andEqualTo(User::getId, 1)
 *            .orEqualTo(User::getUsername, "张三")
 *            .orNotLike("avatar", "aaa")
 *            .orIsNull(User::getBirthday)
 *            .orBetween(User::getRegisterTime, new Date(), new Date())
 *            .orIn("mobile", Arrays.asList(111, "aaa", 222));
 *  example.setDistinct(true);
 *  mapper.selectByExample(example)
 *
 * // SQL语句：
 * // select distinct id, username, birthday, register_time, avatar
 * // from weshop_user
 * // WHERE (id = ? and username = ? or avatar not like ? or birthday is null or register_time between ? and ? or mobile in (?, ?, ?))
 *
 * // 二、多个Criteria语句
 * Example&lt;User&gt; example = Example.of(User.class);
 * example.or()
 *             .orEqualTo(User::getUsername, "bbb")
 *             .andEqualTo(User::getId, 2);
 * example.or()
 *            .andEqualTo(User::getUsername, "aaa");
 * example.and()
 *           .andLessThanOrEqualTo(User::getId, 1000)
 *           .andGreaterThanOrEqualTo(User::getId, 1);
 * Criteria&lt;User&gt; criteria = new Criteria&lt;&gt;();
 * criteria.andIsNull("mobile").andLessThan(User::getNickname,"测试");
 * example.and(criteria);
 * // 排序
 * example.setSort(Sort.by("name").and("age", Direction.DESC));
 * // 可以和Page对象一起使用
 * List&lt;User&gt; users = Page.of(1,3).list(()-&gt; mapper.selectByExample(example));
 *
 * // SQL语句：
 * // select  id, username, birthday, register_time, avatar
 * // from weshop_user
 * // WHERE  (username = ? and id = ?)
 * //       or   (username = ?)
 * //      and   (id &lt;= ? and id &gt;= ?)
 * //      and   ( mobile is null and nickname &lt; ? )
 * </pre>
 *
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
        criteria.andOr("AND");
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria<T> and(Criteria<T> criteria) {
        oredCriteria.add(criteria);
        criteria.andOr("AND");
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

    @Deprecated
    public List<String> getColumns() {
        return columns;
    }

    /**
     * 为了保证数据完整性，不推荐自定义查询字段
     *
     * @param properties
     * @return
     * @since 1.6.6
     */
    @Deprecated
    public Example<T> setSelects(Property<T, ?>... properties) {
        this.columns = Arrays.stream(properties)
            .map(p -> p.getColumnName(entityClass.getName()))
            .collect(Collectors.toList());
        return this;
    }

    /**
     * 为了保证数据完整性，不推荐自定义查询字段
     *
     * @param properties
     * @return
     * @since 1.6.6
     */
    @Deprecated
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
            List<Sort.Order> orders = sort.getOrders();
            this.orderByClause = orders.stream()
                    .map(order -> EntityMappingUtil.getColumnName(entityClass.getName(), order.getProperty()).concat(" ").concat(order.getDirection().toString()))
                    .collect(Collectors.joining(","));
        }
        return this;
    }

    @Deprecated
    public Example<T> setSort(tech.wetech.mybatis.example.Sort sort) {
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
