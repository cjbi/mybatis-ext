package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.SELECT)
public @interface SelectEntityProvider {

    Class<?> type();

    String method();
}
