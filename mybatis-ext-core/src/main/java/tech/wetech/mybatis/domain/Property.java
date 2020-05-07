package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author cjbi
 */
public interface Property<T, R> extends Function<T, R>, Serializable {

    default String getColumnName(String className) {
        return EntityMappingUtil.getColumnName(className, this);
    }

    default String getPropertyName() {
        return EntityMappingUtil.getFunctionName(this);
    }

}
