package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EntityProviderSqlCommandType {
    SqlCommandType value();
}
