package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * 指定提供用于删除记录的SQL的实体类映射方法
 * <br>
 * <br>
 * <b>怎样使用:<b/>
 * <pre>
 * public interface MyMapper&lt;T&gt; {
 *     &#064;SelectEntityProvider(type = MyEntitySqlBuilder.class, method = "selectById")
 *     T selectById(Integer id);
 *
 *     class MyEntitySqlBuilder {
 *       public String selectById(EntityMapping entityMapping) {
 *           return "SELECT * FROM "+entityMapping.getTableName()+ " WHERE id = #{id}";
 *      }
 * }
 * </pre>
 *
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.SELECT)
public @interface SelectEntityProvider {
    /**
     * 提供提供SQL的实体类映射类
     *
     * @return 类类型
     */
    Class<?> type();

    /**
     * 指定提供SQL的实体类映射方法
     *
     * @return 方法名称
     */
    String method();
}
