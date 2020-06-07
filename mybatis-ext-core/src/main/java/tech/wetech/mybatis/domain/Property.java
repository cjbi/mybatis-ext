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
     * @param className 类名称 例如：tech.wetech.mybatis.entity.Goods
     * @return 字段名称
     */
    default String getColumnName(String className) {
        return EntityMappingUtil.getColumnName(className, this);
    }

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    default String getPropertyName() {
        return EntityMappingUtil.getFunctionName(this);
    }

}
