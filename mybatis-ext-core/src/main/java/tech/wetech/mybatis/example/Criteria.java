package tech.wetech.mybatis.example;

import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 */
public class Criteria<T> implements Serializable {

    private final List<Criterion> criterions;

    public Criteria() {
        this.criterions = new ArrayList<>();
    }

    public Criteria<T> andEqualTo(Property<T, ?> property, Object value) {
        andEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andNotEqualTo(Property<T, ?> property, Object value) {
        andNotEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andGreaterThan(Property<T, ?> property, Object value) {
        andGreaterThan(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        andGreaterThanOrEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andLessThan(Property<T, ?> property, Object value) {
        andLessThan(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andLessThanOrEqualTo(Property<T, ?> property, Object value) {
        andLessThan(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andIn(Property<T, ?> property, Iterable<?> values) {
        andIn(EntityMappingUtil.getFunctionName(property), values);
        return this;
    }

    public Criteria<T> andNotIn(Property<T, ?> property, Iterable<?> values) {
        andNotIn(EntityMappingUtil.getFunctionName(property), values);
        return this;
    }

    public Criteria<T> andBetween(Property<T, ?> property, Object value1, Object value2) {
        andBetween(EntityMappingUtil.getFunctionName(property), value1, value2);
        return this;
    }

    public Criteria<T> andNotBetween(Property<T, ?> property, Object value1, Object value2) {
        andNotBetween(EntityMappingUtil.getFunctionName(property), value1, value2);
        return this;
    }

    public Criteria<T> andLike(Property<T, ?> property, String value) {
        andLike(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andNotLike(Property<T, ?> property, String value) {
        andLike(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andIsNull(Property<T, ?> property) {
        andIsNull(EntityMappingUtil.getFunctionName(property));
        return this;
    }

    public Criteria<T> andIsNotNull(Property<T, ?> property) {
        andIsNotNull(EntityMappingUtil.getFunctionName(property));
        return this;
    }

    public Criteria<T> orIsNull(Property<T, ?> property) {
        orIsNull(EntityMappingUtil.getFunctionName(property));
        return this;
    }

    public Criteria<T> orIsNotNull(Property<T, ?> property) {
        orIsNotNull(EntityMappingUtil.getFunctionName(property));
        return this;
    }

    public Criteria<T> orEqualTo(Property<T, ?> property, Object value) {
        orEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orNotEqualTo(Property<T, ?> property, Object value) {
        orNotEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orGreaterThan(Property<T, ?> property, Object value) {
        orGreaterThan(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        orGreaterThanOrEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orLessThan(Property<T, ?> property, Object value) {
        orGreaterThanOrEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orLessThanOrEqualTo(Property<T, ?> property, Object value) {
        orLessThanOrEqualTo(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orIn(Property<T, ?> property, Iterable<?> values) {
        orIn(EntityMappingUtil.getFunctionName(property), values);
        return this;
    }

    public Criteria<T> orNotIn(Property<T, ?> property, Iterable<?> values) {
        orNotIn(EntityMappingUtil.getFunctionName(property), values);
        return this;
    }

    public Criteria<T> orBetween(Property<T, ?> property, Object value1, Object value2) {
        orBetween(EntityMappingUtil.getFunctionName(property), value1, value2);
        return this;
    }

    public Criteria<T> orNotBetween(Property<T, ?> property, Object value1, Object value2) {
        orNotBetween(EntityMappingUtil.getFunctionName(property), value1, value2);
        return this;
    }

    public Criteria<T> orLike(Property<T, ?> property, String value) {
        orLike(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> orNotLike(Property<T, ?> property, String value) {
        orNotLike(EntityMappingUtil.getFunctionName(property), value);
        return this;
    }

    public Criteria<T> andEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "=", "and"));
        return this;
    }

    public Criteria<T> andNotEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<>", "and"));
        return this;
    }

    public Criteria<T> andGreaterThan(String property, Object value) {
        this.criterions.add(new Criterion(property, value, ">", "and"));
        return this;
    }

    public Criteria<T> andGreaterThanOrEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, ">=", "and"));
        return this;
    }

    public Criteria<T> andLessThan(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<", "and"));
        return this;
    }

    public Criteria<T> andLessThanOrEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<=", "and"));
        return this;
    }

    public Criteria<T> andIn(String property, Iterable<?> values) {
        this.criterions.add(new Criterion(property, values, "in", "and"));
        return this;
    }

    public Criteria<T> andNotIn(String property, Iterable<?> values) {
        this.criterions.add(new Criterion(property, values, "not in", "and"));
        return this;
    }

    public Criteria<T> andBetween(String property, Object value1, Object value2) {
        this.criterions.add(new Criterion(property, value1, value2, "between", "and"));
        return this;
    }

    public Criteria<T> andNotBetween(String property, Object value1, Object value2) {
        this.criterions.add(new Criterion(property, value1, value2, "not between", "and"));
        return this;
    }

    public Criteria<T> andLike(String property, String value) {
        this.criterions.add(new Criterion(property, value, "like", "and"));
        return this;
    }

    public Criteria<T> andNotLike(String property, String value) {
        this.criterions.add(new Criterion(property, value, "not like", "and"));
        return this;
    }

    public Criteria<T> andIsNull(String property) {
        this.criterions.add(new Criterion(property, "is null", "and"));
        return this;
    }

    public Criteria<T> andIsNotNull(String property) {
        this.criterions.add(new Criterion(property, "is not null", "and"));
        return this;
    }

    public Criteria<T> orIsNull(String property) {
        this.criterions.add(new Criterion(property, "is null", "or"));
        return this;
    }

    public Criteria<T> orIsNotNull(String property) {
        this.criterions.add(new Criterion(property, "is not null", "or"));
        return this;
    }

    public Criteria<T> orEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "=", "or"));
        return this;
    }

    public Criteria<T> orNotEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<>", "or"));
        return this;
    }

    public Criteria<T> orGreaterThan(String property, Object value) {
        this.criterions.add(new Criterion(property, value, ">", "or"));
        return this;
    }

    public Criteria<T> orGreaterThanOrEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, ">=", "or"));
        return this;
    }

    public Criteria<T> orLessThan(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<", "or"));
        return this;
    }

    public Criteria<T> orLessThanOrEqualTo(String property, Object value) {
        this.criterions.add(new Criterion(property, value, "<=", "or"));
        return this;
    }

    public Criteria<T> orIn(String property, Iterable<?> values) {
        this.criterions.add(new Criterion(property, values, "in", "or"));
        return this;
    }

    public Criteria<T> orNotIn(String property, Iterable<?> values) {
        this.criterions.add(new Criterion(property, values, "not in", "or"));
        return this;
    }

    public Criteria<T> orBetween(String property, Object value1, Object value2) {
        this.criterions.add(new Criterion(property, value1, value2, "between", "or"));
        return this;
    }

    public Criteria<T> orNotBetween(String property, Object value1, Object value2) {
        this.criterions.add(new Criterion(property, value1, value2, "not between", "or"));
        return this;
    }

    public Criteria<T> orLike(String property, String value) {
        this.criterions.add(new Criterion(property, value, "like", "or"));
        return this;
    }

    public Criteria<T> orNotLike(String property, String value) {
        this.criterions.add(new Criterion(property, value, "not like", "or"));
        return this;
    }

    public List<Criterion> getCriterions() {
        return criterions;
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
        criterions.clear();
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "criterions=" + criterions +
                '}';
    }

}
