package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.util.EntityMappingUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 属性
 *
 * @author cjbi
 */
public interface Property<T, R> extends Function<T, R>, Serializable {
    /**
     * 获取字段名称
     *
     * @param className
     * @return
     */
    default String getColumnName(String className) {
        return EntityMappingUtil.getColumnName(className, this);
    }

    /**
     * 获取属性名称
     *
     * @return
     */
    default String getPropertyName() {
        return EntityMappingUtil.getFunctionName(this);
    }

}
