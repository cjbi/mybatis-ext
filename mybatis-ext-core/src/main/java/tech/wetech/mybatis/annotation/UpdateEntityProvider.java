package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.UPDATE)
public @interface UpdateEntityProvider {

    Class<?> type();

    String method();
}
