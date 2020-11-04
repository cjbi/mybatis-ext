package tech.wetech.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 该注解标识字段逻辑删除
 * 默认"0"为正常状态，"1"为删除状态
 *
 * @author cjbi
 * @deprecated 请使用@Where注解实现
 */
@Deprecated
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface LogicDelete {
    /**
     * 设置正常状态的值
     *
     * @return 正常状态的值
     */
    String normalValue() default "0";

    /**
     * 设置删除状态的值
     *
     * @return 删除状态的值
     */
    String deletedValue() default "1";
}
