package tech.wetech.mybatis.util;

import org.apache.ibatis.binding.BindingException;
import tech.wetech.mybatis.builder.EntityMapperBuilder;
import tech.wetech.mybatis.builder.EntityMapping;
import tech.wetech.mybatis.domain.Property;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public class EntityMappingUtil {

    /**
     * 通过注解查找实体属性
     *
     * @param entityMapping
     * @param clazz
     * @return
     */
    public static List<EntityMapping.ColumnProperty> findAnnotationColumnProperty(EntityMapping entityMapping, Class<? extends Annotation> clazz) {
        return entityMapping.getColumnProperties()
                .stream()
                .filter(columnProperty -> columnProperty.getAnnotation(clazz) != null)
                .collect(Collectors.toList());
    }

    /**
     * 通过注解查找一个实体属性
     *
     * @param entityMapping
     * @param clazz
     * @return
     */
    public static EntityMapping.ColumnProperty findAnnotationColumnPropertyOne(EntityMapping entityMapping, Class<? extends Annotation> clazz) {
        return entityMapping.getColumnProperties()
                .stream()
                .filter(columnProperty -> columnProperty.getAnnotation(clazz) != null)
                .findFirst()
                .orElse(null);
    }

    public static String getColumnName(String className, String property) {
        EntityMapping entityMapping = EntityMapperBuilder.TABLE_ENTITY_CACHE.get(className);
        Map<String, EntityMapping.ColumnProperty> columnPropertyMap = entityMapping.getColumnPropertyMap();
        EntityMapping.ColumnProperty columnProperty = columnPropertyMap.get(property);
        if (columnProperty == null) {
            throw new BindingException("Property " + property + " is not mapping to the EntityMapping.");
        }
        return columnProperty.getColumnName();
    }

    public static <FN extends Function> String getColumnName(String className, FN fn) {
        return getColumnName(className, getFunctionName(fn));
    }

    public static Class<?> extractEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedType target = null;
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                Type[] typeArray = ((ParameterizedType) type).getActualTypeArguments();
                if (typeArray != null && typeArray.length > 0) {
                    for (Type t : typeArray) {
                        if (t instanceof TypeVariable || t instanceof WildcardType) {
                            break;
                        } else {
                            target = (ParameterizedType) type;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return target == null ? null : (Class<?>) target.getActualTypeArguments()[0];
    }

    public static <FN extends Function> String getFunctionName(FN fn) {
        try {
            Method declaredMethod = fn.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(fn);
            String method = serializedLambda.getImplMethodName();
            String attr = null;
            if (method.startsWith("get")) {
                attr = method.substring(3);
            } else {
                attr = method.substring(2);
            }
            return Introspector.decapitalize(attr);
        } catch (ReflectiveOperationException var6) {
            throw new RuntimeException(var6);
        }
    }

    public static String[] getStringProperties(Property<?, ?>... properties) {
        String[] stringProperties = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            stringProperties[i] = properties[i].getPropertyName();
        }
        return stringProperties;
    }

}
