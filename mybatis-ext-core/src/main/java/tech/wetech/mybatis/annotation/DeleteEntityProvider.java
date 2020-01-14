package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.DELETE)
public @interface DeleteEntityProvider {
    Class<?> type();
    String method();
}
