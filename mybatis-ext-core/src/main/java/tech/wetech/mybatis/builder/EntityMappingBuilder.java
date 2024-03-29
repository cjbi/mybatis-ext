package tech.wetech.mybatis.builder;

import org.apache.ibatis.session.Configuration;
import tech.wetech.mybatis.annotation.Where;
import tech.wetech.mybatis.builder.EntityMapping.ColumnProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体类映射Builder
 *
 * @author cjbi
 */
public class EntityMappingBuilder {

    private final Class<?> entityClass;

    private final Configuration configuration;

    private final EntityMapping entityMapping;

    public EntityMappingBuilder(Class<?> entityClass, Configuration configuration) {
        this.entityClass = entityClass;
        this.configuration = configuration;
        entityMapping = new EntityMapping();
    }

    public EntityMapping build() {
        entityMapping.setEntityClass(entityClass);
        entityMapping.setTableName(getTableName());
        List<ColumnProperty> columnProperties = getColumnProperties();
        entityMapping.setColumnProperties(columnProperties);
        entityMapping.setKeyProperty(buildKeyProperty(columnProperties));
        entityMapping.setKeyColumn(buildKeyColumn(columnProperties));
        entityMapping.setKeyResultType(buildKeyResultType(columnProperties));
        entityMapping.setColumnPropertyMap(buildColumnPropertiesMap(columnProperties));
        entityMapping.setAnnotationMap(Arrays.stream(entityClass.getAnnotations()).collect(Collectors.toMap(Annotation::annotationType, annotation -> annotation, (a1, a2) -> a2)));
        entityMapping.setWhereClause(getWhereClause());
        return entityMapping;
    }

    private Map<String, ColumnProperty> buildColumnPropertiesMap(List<ColumnProperty> columnProperties) {
        Map<String, ColumnProperty> columnPropertyMap = new HashMap<>();
        for (ColumnProperty columnProperty : columnProperties) {
            columnPropertyMap.put(columnProperty.getPropertyName(), columnProperty);
        }
        return columnPropertyMap;
    }

    private String getWhereClause() {
        if (entityClass.isAnnotationPresent(Where.class)) {
            Where where = getRequiredAnnotation(Where.class);
            return where.clause();
        }
        return null;
    }

    private String getTableName() {
        StringBuilder builder = new StringBuilder();
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = getRequiredAnnotation(Table.class);
            if (!table.catalog().isEmpty()) {
                builder.append(table.catalog()).append('.');
            }
            if (!table.schema().isEmpty()) {
                builder.append(table.schema()).append('.');
            }
            if (!table.name().isEmpty()) {
                builder.append(table.name());
            } else {
                builder.append(camelhumpToUnderline(entityClass.getSimpleName()));
            }
        } else {
            builder.append(camelhumpToUnderline(entityClass.getSimpleName()));
        }
        return builder.toString();
    }

    private List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> tempClass = entityClass;
        while (tempClass != null && !tempClass.equals(Object.class)) {
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        return fields;
    }

    private List<ColumnProperty> getColumnProperties() {
        List<Field> fields = getAllFields();
        List<ColumnProperty> columnProperties = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (!configuration.getTypeHandlerRegistry().hasTypeHandler(field.getType())) {
                continue;
            }
            ColumnProperty columnProperty = new ColumnProperty();
            columnProperty.setPropertyName(field.getName());
            columnProperty.setJavaType(field.getType());
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnProperty.setColumnName(column.name());
            } else {
                String s = camelhumpToUnderline(field.getName());
                columnProperty.setColumnName(s);
            }
            if (field.isAnnotationPresent(Id.class)) {
                columnProperty.setIdentity(true);
            }
            columnProperty.setAnnotationMap(Arrays.stream(field.getAnnotations()).collect(Collectors.toMap(Annotation::annotationType, annotation -> annotation, (a1, a2) -> a2)));
            columnProperties.add(columnProperty);
        }
        return columnProperties;
    }

    private <A extends Annotation> A getRequiredAnnotation(Class<A> annotationType) throws IllegalStateException {
        A annotation = entityClass.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        throw new IllegalStateException(
            String.format("Required annotation %s not found for %s!", annotationType, annotationType.getName()));
    }

    private String buildKeyProperty(List<ColumnProperty> columnProperties) {
        for (ColumnProperty columnProperty : columnProperties) {
            if (columnProperty.isIdentity()) {
                return columnProperty.getPropertyName();
            }
        }
        return null;
    }

    private String buildKeyColumn(List<ColumnProperty> columnProperties) {
        for (ColumnProperty columnProperty : columnProperties) {
            if (columnProperty.isIdentity()) {
                return columnProperty.getColumnName();
            }
        }
        return null;
    }

    private Class<?> buildKeyResultType(List<ColumnProperty> columnProperties) {
        for (ColumnProperty columnProperty : columnProperties) {
            if (columnProperty.isIdentity()) {
                return columnProperty.getJavaType();
            }
        }
        return null;
    }

    /**
     * 驼峰转下划线
     *
     * @param str 字符串
     * @return 驼峰转下划线
     */
    private String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
            (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(toLowerAscii(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }

}
