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
 *     &#064;SelectEntityProvider(type = MyEntitySqlBuilder.class, method = "updateByPrimaryKey")
 *     T updateByPrimaryKey(Integer id);
 *     class MyEntitySqlBuilder {
 *       public String updateByPrimaryKey(EntityMapping entityMapping) {
 *         EntityMapping.ColumnProperty logicDeleteProperty = EntityMappingUtil.findAnnotationColumnPropertyOne(entityMapping, LogicDelete.class);
 *         EntityMapping.ColumnProperty versionProperty = EntityMappingUtil.findAnnotationColumnPropertyOne(entityMapping, Version.class);
 *         StringBuilder builder = new StringBuilder("&lt;script&gt;");
 *         StringBuilder setBuilder = new StringBuilder("&lt;set&gt;");
 *         for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
 *             if (versionProperty != null && versionProperty.getPropertyName().equals(columnProperty.getPropertyName())) {
 *                 setBuilder.append(String.format("%s = %s + 1,", columnProperty.getColumnName(), columnProperty.getColumnName()));
 *                 continue;
 *             }
 *             setBuilder.append(String.format("%s = #{%s},", columnProperty.getColumnName(), columnProperty.getPropertyName()));
 *         }
 *         setBuilder.append("&lt;/set&gt;");
 *         builder.append(String.format("UPDATE %s %s WHERE %s = #{%s}", entityMapping.getTableName(), setBuilder, entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
 *         if (logicDeleteProperty != null) {
 *             builder.append(String.format(" and %s = %s", logicDeleteProperty.getColumnName(), logicDeleteProperty.getAnnotation(LogicDelete.class).normalValue()));
 *         }
 *         if (versionProperty != null) {
 *             builder.append(String.format("&lt;if test='%s != null'&gt; and %s = #{%s}&lt;/if&gt;", versionProperty.getPropertyName(), versionProperty.getColumnName(), versionProperty.getPropertyName()));
 *         }
 *         builder.append("&lt;/script&gt;");
 *         return builder.toString();
 *     }
 * }
 * </pre>
 *
 * @author cjbi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EntityProviderSqlCommandType(SqlCommandType.UPDATE)
public @interface UpdateEntityProvider {
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
