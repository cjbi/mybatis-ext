package tech.wetech.mybatis.example;

import tech.wetech.mybatis.domain.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件
 *
 * @author cjbi
 * @see Example
 */
public class Criteria<T> implements Serializable {

    private static final String DEFAULT_AND_OR = "OR";
    private final List<Criterion> criteria;
    private String andOr = DEFAULT_AND_OR;

    public Criteria() {
        this.criteria = new ArrayList<>();
    }

    public String getAndOr() {
        return andOr;
    }

    public void andOr(String andOr) {
        this.andOr = andOr;
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    private void addCriterion(String property, String condition, String andOr) {
        if (property == null) {
            throw new RuntimeException("Property cannot be null");
        }
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new Criterion(property, condition, andOr));
    }


    private void addCriterion(String property, Object value, String condition, String andOr) {
        if (property == null) {
            throw new RuntimeException("Property cannot be null");
        }
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        criteria.add(new Criterion(property, value, condition, andOr));
    }

    private void addCriterion(String property, Object value1, Object value2, String condition, String andOr) {
        if (property == null) {
            throw new RuntimeException("Property cannot be null");
        }
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        criteria.add(new Criterion(property, value1, value2, condition, andOr));
    }


    public Criteria<T> andEqualTo(Property<T, ?> property, Object value) {
        andEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andNotEqualTo(Property<T, ?> property, Object value) {
        andNotEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andGreaterThan(Property<T, ?> property, Object value) {
        andGreaterThan(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        andGreaterThanOrEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andLessThan(Property<T, ?> property, Object value) {
        andLessThan(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andLessThanOrEqualTo(Property<T, ?> property, Object value) {
        andLessThanOrEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andIn(Property<T, ?> property, Iterable<?> values) {
        andIn(property.getPropertyName(), values);
        return this;
    }

    public Criteria<T> andNotIn(Property<T, ?> property, Iterable<?> values) {
        andNotIn(property.getPropertyName(), values);
        return this;
    }

    public Criteria<T> andBetween(Property<T, ?> property, Object value1, Object value2) {
        andBetween(property.getPropertyName(), value1, value2);
        return this;
    }

    public Criteria<T> andNotBetween(Property<T, ?> property, Object value1, Object value2) {
        andNotBetween(property.getPropertyName(), value1, value2);
        return this;
    }

    public Criteria<T> andLike(Property<T, ?> property, String value) {
        andLike(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andNotLike(Property<T, ?> property, String value) {
        andNotLike(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andIsNull(Property<T, ?> property) {
        andIsNull(property.getPropertyName());
        return this;
    }

    public Criteria<T> andIsNotNull(Property<T, ?> property) {
        andIsNotNull(property.getPropertyName());
        return this;
    }

    public Criteria<T> orIsNull(Property<T, ?> property) {
        orIsNull(property.getPropertyName());
        return this;
    }

    public Criteria<T> orIsNotNull(Property<T, ?> property) {
        orIsNotNull(property.getPropertyName());
        return this;
    }

    public Criteria<T> orEqualTo(Property<T, ?> property, Object value) {
        orEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orNotEqualTo(Property<T, ?> property, Object value) {
        orNotEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orGreaterThan(Property<T, ?> property, Object value) {
        orGreaterThan(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        orGreaterThanOrEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orLessThan(Property<T, ?> property, Object value) {
        orLessThan(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orLessThanOrEqualTo(Property<T, ?> property, Object value) {
        orLessThanOrEqualTo(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orIn(Property<T, ?> property, Iterable<?> values) {
        orIn(property.getPropertyName(), values);
        return this;
    }

    public Criteria<T> orNotIn(Property<T, ?> property, Iterable<?> values) {
        orNotIn(property.getPropertyName(), values);
        return this;
    }

    public Criteria<T> orBetween(Property<T, ?> property, Object value1, Object value2) {
        orBetween(property.getPropertyName(), value1, value2);
        return this;
    }

    public Criteria<T> orNotBetween(Property<T, ?> property, Object value1, Object value2) {
        orNotBetween(property.getPropertyName(), value1, value2);
        return this;
    }

    public Criteria<T> orLike(Property<T, ?> property, String value) {
        orLike(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> orNotLike(Property<T, ?> property, String value) {
        orNotLike(property.getPropertyName(), value);
        return this;
    }

    public Criteria<T> andEqualTo(String property, Object value) {
        addCriterion(property, value, "=", "AND");
        return this;
    }

    public Criteria<T> andNotEqualTo(String property, Object value) {
        addCriterion(property, value, "<>", "AND");
        return this;
    }

    public Criteria<T> andGreaterThan(String property, Object value) {
        addCriterion(property, value, ">", "AND");
        return this;
    }

    public Criteria<T> andGreaterThanOrEqualTo(String property, Object value) {
        addCriterion(property, value, ">=", "AND");
        return this;
    }

    public Criteria<T> andLessThan(String property, Object value) {
        addCriterion(property, value, "<", "AND");
        return this;
    }

    public Criteria<T> andLessThanOrEqualTo(String property, Object value) {
        addCriterion(property, value, "<=", "AND");
        return this;
    }

    public Criteria<T> andIn(String property, Iterable<?> values) {
        addCriterion(property, values, "IN", "AND");
        return this;
    }

    public Criteria<T> andNotIn(String property, Iterable<?> values) {
        addCriterion(property, values, "NOT IN", "AND");
        return this;
    }

    public Criteria<T> andBetween(String property, Object value1, Object value2) {
        addCriterion(property, value1, value2, "BETWEEN", "AND");
        return this;
    }

    public Criteria<T> andNotBetween(String property, Object value1, Object value2) {
        addCriterion(property, value1, value2, "NOT BETWEEN", "AND");
        return this;
    }

    public Criteria<T> andLike(String property, String value) {
        addCriterion(property, value, "LIKE", "AND");
        return this;
    }

    public Criteria<T> andNotLike(String property, String value) {
        addCriterion(property, value, "NOT LIKE", "AND");
        return this;
    }

    public Criteria<T> andIsNull(String property) {
        addCriterion(property, "IS NULL", "AND");
        return this;
    }

    public Criteria<T> andIsNotNull(String property) {
        addCriterion(property, "IS NOT NULL", "AND");
        return this;
    }

    public Criteria<T> orIsNull(String property) {
        addCriterion(property, "IS NULL", "OR");
        return this;
    }

    public Criteria<T> orIsNotNull(String property) {
        addCriterion(property, "IS NOT NULL", "OR");
        return this;
    }

    public Criteria<T> orEqualTo(String property, Object value) {
        addCriterion(property, value, "=", "OR");
        return this;
    }

    public Criteria<T> orNotEqualTo(String property, Object value) {
        addCriterion(property, value, "<>", "OR");
        return this;
    }

    public Criteria<T> orGreaterThan(String property, Object value) {
        addCriterion(property, value, ">", "OR");
        return this;
    }

    public Criteria<T> orGreaterThanOrEqualTo(String property, Object value) {
        addCriterion(property, value, ">=", "OR");
        return this;
    }

    public Criteria<T> orLessThan(String property, Object value) {
        addCriterion(property, value, "<", "OR");
        return this;
    }

    public Criteria<T> orLessThanOrEqualTo(String property, Object value) {
        addCriterion(property, value, "<=", "OR");
        return this;
    }

    public Criteria<T> orIn(String property, Iterable<?> values) {
        addCriterion(property, values, "IN", "OR");
        return this;
    }

    public Criteria<T> orNotIn(String property, Iterable<?> values) {
        addCriterion(property, values, "NOT IN", "OR");
        return this;
    }

    public Criteria<T> orBetween(String property, Object value1, Object value2) {
        addCriterion(property, value1, value2, "BETWEEN", "OR");
        return this;
    }

    public Criteria<T> orNotBetween(String property, Object value1, Object value2) {
        addCriterion(property, value1, value2, "NOT BETWEEN", "OR");
        return this;
    }

    public Criteria<T> orLike(String property, String value) {
        addCriterion(property, value, "LIKE", "OR");
        return this;
    }

    public Criteria<T> orNotLike(String property, String value) {
        addCriterion(property, value, "NOT LIKE", "OR");
        return this;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public class Criterion {

        private String property;
        private Object value;
        private Object secondValue;
        private String condition;
        private String andOr;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;

        public Criterion(String property, String condition, String andOr) {
            this.property = property;
            this.condition = condition;
            this.andOr = andOr;
            this.noValue = true;
        }


        public Criterion(String property, Object value, String condition, String andOr) {
            this.property = property;
            this.value = value;
            this.condition = condition;
            this.andOr = andOr;

            if (value instanceof Iterable) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        public Criterion(String property, Object value1, Object value2, String condition, String andOr) {
            this.property = property;
            this.value = value1;
            this.secondValue = value2;
            this.condition = condition;
            this.andOr = andOr;
            this.betweenValue = true;
        }

        public String getProperty() {
            return property;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public String getCondition() {
            return condition;
        }

        public String getAndOr() {
            return andOr;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        @Override
        public String toString() {
            return "Criterion{" +
                    "property='" + property + '\'' +
                    ", value=" + value +
                    ", secondValue=" + secondValue +
                    ", condition='" + condition + '\'' +
                    ", andOr='" + andOr + '\'' +
                    ", noValue=" + noValue +
                    ", singleValue=" + singleValue +
                    ", betweenValue=" + betweenValue +
                    ", listValue=" + listValue +
                    '}';
        }
    }

    public void clear() {
        criteria.clear();
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "criteria=" + criteria +
                '}';
    }

}
