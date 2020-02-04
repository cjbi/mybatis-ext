package tech.wetech.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author cjbi
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface LogicDelete {
    String normalValue() default "0";
    String deletedValue() default "1";
}
