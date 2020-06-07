package tech.wetech.mybatis;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 参数查找
 *
 * @author cjbi
 */
public class ParametersFinder {

    private static ParametersFinder parametersFinder = null;

    public static ParametersFinder getInstance() {
        if (parametersFinder != null) {
            return parametersFinder;
        }
        return new ParametersFinder();
    }

    /**
     * 根据类型查找参数
     *
     * @param parameterObject MyBatis入参
     * @param clazz           要查找的类类型
     * @param <T>             要查找的类型返回
     * @return 实体类型参数
     */
    public <T> T findParameter(Object parameterObject, Class<T> clazz) {
        return findParameterFromObject(parameterObject, clazz);
    }

    public <T> T findParameterFromObject(Object parameterObject, Class<T> clazz) {
        if (parameterObject == null) {
            return null;
        }
        Class<?> pClass = parameterObject.getClass();
        if (clazz == pClass || clazz.isAssignableFrom(pClass)) {
            return (T) parameterObject;
        } else if (parameterObject instanceof Map) {
            return findParameterFromMap((Map) parameterObject, clazz);
        } else if (parameterObject instanceof Collection) {
            return findParameterFromCollection((Collection) parameterObject, clazz);
        } else if (pClass.isArray()) {
            return findParameterFromArray(parameterObject, clazz);
        }
        return null;
    }

    public <T> T findParameterFromMap(Map map, Class<T> clazz) {
        for (Object value : map.values()) {
            T parameterFromObject = findParameterFromObject(value, clazz);
            if (parameterFromObject != null) {
                return parameterFromObject;
            }
        }
        return null;
    }

    public <T> T findParameterFromCollection(Collection collection, Class<T> clazz) {
        for (Object value : collection) {
            T parameterFromObject = findParameterFromObject(value, clazz);
            if (parameterFromObject != null) {
                return parameterFromObject;
            }
        }
        return null;
    }

    private <T> T findParameterFromArray(Object array, Class<T> clazz) {
        for (int i = 0; i < Array.getLength(array); i++) {
            T parameterFromObject = findParameterFromObject(Array.get(array, i), clazz);
            if (parameterFromObject != null) {
                return parameterFromObject;
            }
        }
        return null;
    }

}
