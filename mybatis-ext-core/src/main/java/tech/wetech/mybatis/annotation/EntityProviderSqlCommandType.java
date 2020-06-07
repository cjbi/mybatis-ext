package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * 指定提供的实体类注解SQL指令类型
 *
 * @author cjbi
 * @see SelectEntityProvider
 * @see DeleteEntityProvider
 * @see InsertEntityProvider
 * @see UpdateEntityProvider
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EntityProviderSqlCommandType {
    /**
     * 指定SQL指令类型
     *
     * @return SQL指令类型
     */
    SqlCommandType value();
}
