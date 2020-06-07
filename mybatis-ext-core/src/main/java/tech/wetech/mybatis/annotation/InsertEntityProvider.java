package tech.wetech.mybatis.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.*;

/**
 * 指定提供用于删除记录的SQL的实体类映射方法
 * <br>
 * <br>
 * <b>怎样使用:</b>
 * <pre>
 * public interface MyMapper&lt;T&gt; {
 *     &#064;InsertEntityProvider(type = MyEntitySqlBuilder.class, method = "insert")
 *     int insert(Integer id);
 *
 *     class MyEntitySqlBuilder {
 *       public String insert(EntityMapping entityMapping) {
 *           StringBuilder builder = new StringBuilder("&lt;script&gt;");
 *           StringBuilder columnsBuilder = new StringBuilder("&lt;trim prefix='(' suffix=')' suffixOverrides=','&gt;");
 *           StringBuilder valuesBuilder = new StringBuilder("&lt;trim prefix='(' suffix=')' suffixOverrides=','&gt;");
 *           for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
 *             columnsBuilder.append(columnProperty.getColumnName()).append(" , ");
 *             valuesBuilder.append("#{").append(columnProperty.getPropertyName()).append("}").append(" , ");
 *           }
 *           columnsBuilder.append("&lt;/trim&gt;");
 *           valuesBuilder.append("&lt;/trim&gt;");
 *           builder.append(String.format("INSERT INTO %s %s VALUES%s", entityMapping.getTableName(), columnsBuilder, valuesBuilder));
 *           builder.append("&lt;/script&gt;");
 *           return builder.toString();
 *     }
 * }
 * </pre>
 *
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.INSERT)
public @interface InsertEntityProvider {
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
