package tech.wetech.mybatis.builder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 表实体映射
 *
 * @author cjbi
 */
public class EntityMapping {

    private Class<?> entityClass;

    private String tableName;

    private List<ColumnProperty> columnProperties;
    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 表主键ID 字段名
     */
    private String keyColumn;

    private Map<String, ColumnProperty> columnPropertyMap;
    /**
     * 是否逻辑删除
     */
    private boolean isLogicDelete;
    /**
     * 逻辑删除列
     */
    private String logicDeleteColumn;

    private String logicDeleteNormalValue;

    private String logicDeleteDeletedValue;
    /**
     * 乐观锁
     */
    private boolean isOptimisticLock;
    /**
     * 乐观锁字段
     */
    private String optimisticLockColumn;
    /**
     * 乐观锁字段
     */
    private String optimisticLockProperty;

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

    public boolean isLogicDelete() {
        return isLogicDelete;
    }

    public void setLogicDelete(boolean logicDelete) {
        isLogicDelete = logicDelete;
    }

    public String getLogicDeleteColumn() {
        return logicDeleteColumn;
    }

    public void setLogicDeleteColumn(String logicDeleteColumn) {
        this.logicDeleteColumn = logicDeleteColumn;
    }

    public String getLogicDeleteNormalValue() {
        return logicDeleteNormalValue;
    }

    public void setLogicDeleteNormalValue(String logicDeleteNormalValue) {
        this.logicDeleteNormalValue = logicDeleteNormalValue;
    }

    public String getLogicDeleteDeletedValue() {
        return logicDeleteDeletedValue;
    }

    public void setLogicDeleteDeletedValue(String logicDeleteDeletedValue) {
        this.logicDeleteDeletedValue = logicDeleteDeletedValue;
    }

    public boolean isOptimisticLock() {
        return isOptimisticLock;
    }

    public void setOptimisticLock(boolean optimisticLock) {
        isOptimisticLock = optimisticLock;
    }

    public String getOptimisticLockColumn() {
        return optimisticLockColumn;
    }

    public void setOptimisticLockColumn(String optimisticLockColumn) {
        this.optimisticLockColumn = optimisticLockColumn;
    }

    public String getOptimisticLockProperty() {
        return optimisticLockProperty;
    }

    public void setOptimisticLockProperty(String optimisticLockProperty) {
        this.optimisticLockProperty = optimisticLockProperty;
    }

    public static class ColumnProperty {

        private String columnName;

        private String propertyName;

        private boolean identity;

        private Class<?> javaType;

        private List<Annotation> annotations;

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

        public List<Annotation> getAnnotations() {
            return annotations;
        }

        public ColumnProperty setAnnotations(List<Annotation> annotations) {
            this.annotations = annotations;
            return this;
        }
    }

}
