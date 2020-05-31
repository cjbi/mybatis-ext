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
 * 实体类映射工具类
 *
 * @author cjbi
 */
public class EntityMappingUtil {

    /**
     * 通过注解查找实体属性
     *
     * @param entityMapping 实体类映射
     * @param clazz 实体类类型
     * @return 查找到的实体属性
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
     * @param entityMapping 实体类映射
     * @param clazz         实体类类型
     * @return 查找到的一个实体属性
     */
    public static EntityMapping.ColumnProperty findAnnotationColumnPropertyOne(EntityMapping entityMapping, Class<? extends Annotation> clazz) {
        return entityMapping.getColumnProperties()
                .stream()
                .filter(columnProperty -> columnProperty.getAnnotation(clazz) != null)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取表字段名
     *
     * @param className 实体类名
     * @param property  实体类属性
     * @return 表字段名
     */
    public static String getColumnName(String className, String property) {
        EntityMapping entityMapping = EntityMapperBuilder.TABLE_ENTITY_CACHE.get(className);
        Map<String, EntityMapping.ColumnProperty> columnPropertyMap = entityMapping.getColumnPropertyMap();
        EntityMapping.ColumnProperty columnProperty = columnPropertyMap.get(property);
        if (columnProperty == null) {
            throw new BindingException("Property " + property + " is not mapping to the EntityMapping.");
        }
        return columnProperty.getColumnName();
    }

    /**
     * 获取表字段名
     *
     * @param className 实体类名
     * @param fn        函数参数
     * @param <FN>      函数名
     * @return 表字段名
     */
    public static <FN extends Function> String getColumnName(String className, FN fn) {
        return getColumnName(className, getFunctionName(fn));
    }

    /**
     * 提取实体Mapper的类类型
     *
     * @param mapperClass 实体Mapper
     * @return 实体类类型
     */
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

    /**
     * 获取属性名
     *
     * @param fn   函数参数
     * @param <FN> 函数名
     * @return 表字段名
     */
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

    /**
     * 获取字符串形式属性列表
     *
     * @param properties 属性列表
     * @return 字符串形式属性列表
     */
    public static String[] getStringProperties(Property<?, ?>... properties) {
        String[] stringProperties = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            stringProperties[i] = properties[i].getPropertyName();
        }
        return stringProperties;
    }

}
