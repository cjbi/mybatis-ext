package tech.wetech.mybatis.example;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tech.wetech.mybatis.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author cjbi
 */
public class MapperCriteria<T> extends Criteria<T> {

    private final BaseMapper<T, ? extends Serializable> mapper;

    private final MapperExample mapperExample;

    public MapperCriteria(BaseMapper<T, ? extends Serializable> mapper) {
        this.mapper = mapper;
        this.mapperExample = new MapperExample(mapper);
        mapperExample.createCriteria(this);
    }

    public MapperCriteria(BaseMapper mapper, MapperExample mapperExample) {
        this.mapper = mapper;
        this.mapperExample = mapperExample;
    }

    public Optional<T> selectOneWithOptional() {
        List<T> list = mapper.selectByExample(mapperExample);
        return list.stream().findFirst();
    }

    public T selectOne() {
        List<T> list = mapper.selectByExample(mapperExample);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }


    public List<T> selectList() {
        return mapper.selectByExample(mapperExample);
    }

    public int count() {
        return mapper.countByExample(mapperExample);
    }

    public int update(T record) {
        return mapper.updateByExample(record, mapperExample);
    }

    public int updateSelective(T record) {
        return mapper.updateByExampleSelective(record, mapperExample);
    }

    public int delete() {
        return mapper.deleteByExample(mapperExample);
    }

    @Override
    public MapperCriteria<T> andEqualTo(Property<T, ?> property, Object value) {
        super.andEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotEqualTo(Property<T, ?> property, Object value) {
        super.andNotEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andGreaterThan(Property<T, ?> property, Object value) {
        super.andGreaterThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        super.andGreaterThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andLessThan(Property<T, ?> property, Object value) {
        super.andLessThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andLessThanOrEqualTo(Property<T, ?> property, Object value) {
        super.andLessThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andIn(Property<T, ?> property, Iterable<?> values) {
        super.andIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotIn(Property<T, ?> property, Iterable<?> values) {
        super.andNotIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> andBetween(Property<T, ?> property, Object value1, Object value2) {
        super.andBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotBetween(Property<T, ?> property, Object value1, Object value2) {
        super.andNotBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> andLike(Property<T, ?> property, String value) {
        super.andLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotLike(Property<T, ?> property, String value) {
        super.andNotLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andIsNull(Property<T, ?> property) {
        super.andIsNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> andIsNotNull(Property<T, ?> property) {
        super.andIsNotNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orIsNull(Property<T, ?> property) {
        super.orIsNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orIsNotNull(Property<T, ?> property) {
        super.orIsNotNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orEqualTo(Property<T, ?> property, Object value) {
        super.orEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotEqualTo(Property<T, ?> property, Object value) {
        super.orNotEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orGreaterThan(Property<T, ?> property, Object value) {
        super.orGreaterThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orGreaterThanOrEqualTo(Property<T, ?> property, Object value) {
        super.orGreaterThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orLessThan(Property<T, ?> property, Object value) {
        super.orLessThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orLessThanOrEqualTo(Property<T, ?> property, Object value) {
        super.orLessThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orIn(Property<T, ?> property, Iterable<?> values) {
        super.orIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotIn(Property<T, ?> property, Iterable<?> values) {
        super.orNotIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> orBetween(Property<T, ?> property, Object value1, Object value2) {
        super.orBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotBetween(Property<T, ?> property, Object value1, Object value2) {
        super.orNotBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> orLike(Property<T, ?> property, String value) {
        super.orLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotLike(Property<T, ?> property, String value) {
        super.orNotLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andEqualTo(String property, Object value) {
        super.andEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotEqualTo(String property, Object value) {
        super.andNotEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andGreaterThan(String property, Object value) {
        super.andGreaterThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andGreaterThanOrEqualTo(String property, Object value) {
        super.andGreaterThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andLessThan(String property, Object value) {
        super.andLessThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andLessThanOrEqualTo(String property, Object value) {
        super.andLessThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andIn(String property, Iterable<?> values) {
        super.andIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotIn(String property, Iterable<?> values) {
        super.andNotIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> andBetween(String property, Object value1, Object value2) {
        super.andBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotBetween(String property, Object value1, Object value2) {
        super.andNotBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> andLike(String property, String value) {
        super.andLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andNotLike(String property, String value) {
        super.andNotLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> andIsNull(String property) {
        super.andIsNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> andIsNotNull(String property) {
        super.andIsNotNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orIsNull(String property) {
        super.orIsNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orIsNotNull(String property) {
        super.orIsNotNull(property);
        return this;
    }

    @Override
    public MapperCriteria<T> orEqualTo(String property, Object value) {
        super.orEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotEqualTo(String property, Object value) {
        super.orNotEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orGreaterThan(String property, Object value) {
        super.orGreaterThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orGreaterThanOrEqualTo(String property, Object value) {
        super.orGreaterThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orLessThan(String property, Object value) {
        super.orLessThan(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orLessThanOrEqualTo(String property, Object value) {
        super.orLessThanOrEqualTo(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orIn(String property, Iterable<?> values) {
        super.orIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotIn(String property, Iterable<?> values) {
        super.orNotIn(property, values);
        return this;
    }

    @Override
    public MapperCriteria<T> orBetween(String property, Object value1, Object value2) {
        super.orBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotBetween(String property, Object value1, Object value2) {
        super.orNotBetween(property, value1, value2);
        return this;
    }

    @Override
    public MapperCriteria<T> orLike(String property, String value) {
        super.orLike(property, value);
        return this;
    }

    @Override
    public MapperCriteria<T> orNotLike(String property, String value) {
        super.orNotLike(property, value);
        return this;
    }

    @Override
    public List<Criterion> getCriterions() {
        return super.getCriterions();
    }

}
