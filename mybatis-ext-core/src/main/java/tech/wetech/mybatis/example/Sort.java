package tech.wetech.mybatis.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 此类已经过时，请使用tech.wetech.mybatis.domain.Sort替代
 *
 * @author cjbi
 * @see tech.wetech.mybatis.domain.Sort
 */
@Deprecated
public class Sort implements Serializable {

    public static final Sort.Direction DEFAULT_DIRECTION;
    private final List<Order> orders;

    static {
        DEFAULT_DIRECTION = Sort.Direction.ASC;
    }

    public Sort(List<Order> orders) {
        this.orders = orders;
    }

    public Sort(Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<>() : Arrays.asList(properties));
    }

    public Sort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    public Sort(Direction direction, List<String> properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
        this.orders = new ArrayList<>(properties.size());
        for (String property : properties) {
            this.orders.add(new Order(direction, property));
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static enum Direction {
        ASC,
        DESC
    }

    public static class Order implements Serializable {

        private final String property;
        private final Direction direction;

        public Order(Direction direction, String property) {
            this.direction = direction;
            this.property = property;
        }

        public Order(String property) {
            this(DEFAULT_DIRECTION, property);
        }

        public String getProperty() {
            return property;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}
