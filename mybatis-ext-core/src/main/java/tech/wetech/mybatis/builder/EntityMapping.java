package tech.wetech.mybatis.builder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 实体类映射
 *
 * @author cjbi
 */
public class EntityMapping {
    /**
     * 实体类类型
     */
    private Class<?> entityClass;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 字段属性列表
     */
    private List<ColumnProperty> columnProperties;
    /**
     * 主键对应实体属性
     */
    private String keyProperty;
    /**
     * 主键字段
     */
    private String keyColumn;
    /**
     * 主键返回类型
     */
    private Class<?> keyResultType;
    /**
     * 字段属性映射Map
     */
    private Map<String, ColumnProperty> columnPropertyMap;
    /**
     * 注解映射Map
     */
    private Map<Class<? extends Annotation>, ? extends Annotation> annotationMap;
    /**
     * 查询条件
     */
    private String whereClause;

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnProperty> getColumnProperties() {
        return columnProperties;
    }

    public void setColumnProperties(List<ColumnProperty> columnProperties) {
        this.columnProperties = columnProperties;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public Map<String, ColumnProperty> getColumnPropertyMap() {
        return columnPropertyMap;
    }

    public void setColumnPropertyMap(Map<String, ColumnProperty> columnPropertyMap) {
        this.columnPropertyMap = columnPropertyMap;
    }

    public ColumnProperty getColumnProperty(String property) {
        return this.columnPropertyMap.get(property);
    }

    public Class<?> getKeyResultType() {
        return keyResultType;
    }

    public void setKeyResultType(Class<?> keyResultType) {
        this.keyResultType = keyResultType;
    }

    public Map<Class<? extends Annotation>, ? extends Annotation> getAnnotationMap() {
        return annotationMap;
    }

    public void setAnnotationMap(Map<Class<? extends Annotation>, ? extends Annotation> annotationMap) {
        this.annotationMap = annotationMap;
    }

    public <T extends Annotation> T getAnnotation(Class<T> clazz) {
        return (T) this.annotationMap.get(clazz);
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    /**
     * 字段属性
     */
    public static class ColumnProperty {
        /**
         * 字段名称
         */
        private String columnName;
        /**
         * 属性名称
         */
        private String propertyName;
        /**
         * 主键字段
         */
        private boolean identity;
        /**
         * 对应Java类型
         */
        private Class<?> javaType;
        /**
         * 注解映射Map
         */
        private Map<Class<? extends Annotation>, ? extends Annotation> annotationMap;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public boolean isIdentity() {
            return identity;
        }

        public void setIdentity(boolean identity) {
            this.identity = identity;
        }

        public Class<?> getJavaType() {
            return javaType;
        }

        public void setJavaType(Class<?> javaType) {
            this.javaType = javaType;
        }

        public Map<Class<? extends Annotation>, ? extends Annotation> getAnnotationMap() {
            return annotationMap;
        }

        public void setAnnotationMap(Map<Class<? extends Annotation>, ? extends Annotation> annotationMap) {
            this.annotationMap = annotationMap;
        }

        public <T extends Annotation> T getAnnotation(Class<T> clazz) {
            return (T) this.annotationMap.get(clazz);
        }
    }

}
