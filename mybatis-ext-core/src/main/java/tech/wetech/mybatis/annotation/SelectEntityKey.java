package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.StatementType;

import java.lang.annotation.*;

/**
 * 指定返回查询的主键
 *
 * @author cjbi
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectEntityKey {
    /**
     * Returns an SQL for retrieving a key value.
     *
     * @return an SQL for retrieving a key value
     */
    String[] statement();

    /**
     * Returns whether retrieves a key value before executing insert/update statement.
     *
     * @return {@code true} if execute before; {@code false} if otherwise
     */
    boolean before();

    /**
     * Returns the statement type to use.
     *
     * @return the statement type
     */
    StatementType statementType() default StatementType.PREPARED;
}

