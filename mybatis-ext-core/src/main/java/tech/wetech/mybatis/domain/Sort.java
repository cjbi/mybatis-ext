package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.util.EntityMappingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 对请求进行排序
 * <p/>
 * <p>
 * 用法：
 * </p>
 * <code><pre>
 * Sort sort = Sort.by("name").and("age", Direction.Descending);
 * Sort sort2 = Sort.by("id","name").desc();
 * Sort sort3 = Sort.asc("name", "age");
 * Sort sort4 = Sort.desc("name", "age");
 * Sort sort5 = Sort.by(Goods::getName, Sort.Direction.DESC).and(Goods::getId);
 * </pre></code>
 *
 * @author cjbi
 */
public class Sort<T> {

    /**
     * 排序方式
     *
     * @author cjbi
     */
    public enum Direction {
        /**
         * 升序（默认）
         */
        ASC,
        /**
         * 降序
         */
        DESC
    }

    /**
     * 排序对象
     *
     * @author cjbi
     */
    public class Order {
        /**
         * 属性字段
         */
        private String property;
        /**
         * 排序方式
         */
        private Direction direction;

        public Order(String property, Direction direction) {
            this.property = property;
            this.direction = direction;
        }

        public Order(String property) {
            this(property, Direction.ASC);
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public Sort.Direction getDirection() {
            return direction;
        }
    }

    private List<Sort.Order> orders = new ArrayList<>();

    private Sort() {
    }

    public static <T> Sort<T> by(String property) {
        return new Sort<T>().and(property);
    }

    public static <T> Sort<T> by(Property<T, ?> property) {
        return by(property.getPropertyName());
    }

    public static <T> Sort<T> by(String property, Direction direction) {
        return new Sort<T>().and(property, direction);
    }

    public static <T> Sort<T> by(Property<T, ?> property, Direction direction) {
        return by(property.getPropertyName(), direction);
    }

    public static <T> Sort<T> by(String... properties) {
        Sort<T> sort = new Sort<>();
        for (String property : properties) {
            sort.and(property);
        }
        return sort;
    }

    public static <T> Sort<T> by(Property<T, ?>... properties) {
        return by(EntityMappingUtil.getStringProperties(properties));
    }

    public static <T> Sort<T> asc(String... properties) {
        return by(properties);
    }

    public static <T> Sort<T> asc(Property<T, ?>... properties) {
        return asc(EntityMappingUtil.getStringProperties(properties));
    }

    public static <T> Sort<T> desc(String... properties) {
        Sort<T> sort = new Sort<>();
        for (String property : properties) {
            sort.and(property, Direction.DESC);
        }
        return sort;
    }

    public static <T> Sort<T> desc(Property<T, ?>... properties) {
        return desc(EntityMappingUtil.getStringProperties(properties));
    }

    public Sort<T> desc() {
        return direction(Direction.DESC);
    }

    public Sort<T> asc() {
        return direction(Direction.ASC);
    }

    public Sort<T> direction(Direction direction) {
        for (Sort.Order order : this.orders) {
            order.direction = direction;
        }
        return this;
    }

    public Sort<T> and(String property) {
        this.orders.add(new Sort<T>.Order(property));
        return this;
    }

    public Sort<T> and(Property<T, ?> property) {
        and(property.getPropertyName());
        return this;
    }

    public Sort<T> and(String property, Direction direction) {
        this.orders.add(new Order(property, direction));
        return this;
    }

    public Sort<T> and(Property<T, ?> property, Direction direction) {
        and(property.getPropertyName(), direction);
        return this;
    }

    public List<Sort.Order> getOrders() {
        return orders;
    }

}
