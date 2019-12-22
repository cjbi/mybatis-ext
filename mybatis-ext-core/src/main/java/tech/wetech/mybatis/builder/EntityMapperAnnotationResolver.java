package tech.wetech.mybatis.builder;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.SqlCommandType;
import tech.wetech.mybatis.annotation.*;
import tech.wetech.mybatis.session.ExtConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
public class EntityMapperAnnotationResolver {

    private final Method method;
    private final ExtConfiguration configuration;
    private final EntityMapping entityMapping;

    private boolean isEntityMethodProvider;
    private SqlCommandType sqlCommandType;
    private String script;

    private final Log log = LogFactory.getLog(EntityMapperAnnotationResolver.class);

    private static final Set<Class<? extends Annotation>> SQL_ENTITY_PROVIDER_ANNOTATION_TYPES = new HashSet<>();

    private final Map<Class, Object> injectArgsMap = new HashMap<>();

    static {
        SQL_ENTITY_PROVIDER_ANNOTATION_TYPES.add(SelectEntityProvider.class);
        SQL_ENTITY_PROVIDER_ANNOTATION_TYPES.add(InsertEntityProvider.class);
        SQL_ENTITY_PROVIDER_ANNOTATION_TYPES.add(UpdateEntityProvider.class);
        SQL_ENTITY_PROVIDER_ANNOTATION_TYPES.add(DeleteEntityProvider.class);
    }

    public EntityMapperAnnotationResolver(Method method, ExtConfiguration configuration, EntityMapping entityMapping) {
        this.method = method;
        this.configuration = configuration;
        this.entityMapping = entityMapping;
        injectArgsMap.put(configuration.getClass(), configuration);
        injectArgsMap.put(entityMapping.getClass(), entityMapping);

        Class<? extends Annotation> sqlEntityProviderAnnotationType = getSqlEntityProviderAnnotationType(method);
        if (sqlEntityProviderAnnotationType != null) {
            Annotation annotation = method.getAnnotation(sqlEntityProviderAnnotationType);
            try {
                Class<?> providerMethodClass = (Class) sqlEntityProviderAnnotationType.getMethod("type").invoke(annotation);
                String providerMethodName = (String) sqlEntityProviderAnnotationType.getMethod("method").invoke(annotation);
                sqlCommandType = sqlEntityProviderAnnotationType.getAnnotation(EntityProviderSqlCommandType.class).value();
                isEntityMethodProvider = true;

                List<Method> providerMethods = Stream.of(providerMethodClass.getMethods()).filter(m -> providerMethodName.equals(m.getName())).collect(Collectors.toList());

                if (providerMethods.size() == 0) {
                    String msg = String.format("Not found method %s in class %s", providerMethodName, providerMethodClass);
                    log.error(msg);
                    throw new BuilderException(msg);
                }
                if (providerMethods.size() > 1) {
                    String msg = String.format("Found multi method %s in class %s", providerMethodName, providerMethodClass);
                    log.error(msg);
                    throw new BuilderException(msg);
                }
                Method providerMethod = providerMethods.get(0);
                Parameter[] parameters = providerMethod.getParameters();
                Object[] args = new Object[parameters.length];
                //inject
                for (int i = 0; i < parameters.length; i++) {
                    args[i] = injectArgsMap.get(parameters[i].getType());
                }
                script = providerMethod.invoke(providerMethodClass.newInstance(), args).toString();

                isEntityMethodProvider = true;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                throw new BuilderException(e);
            }
        } else {
            isEntityMethodProvider = false;
        }

    }

    public boolean isEntityMethodProvider() {
        return isEntityMethodProvider;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public String getScript() {
        return script;
    }

    public Method getMethod() {
        return method;
    }

    public ExtConfiguration getConfiguration() {
        return configuration;
    }

    public EntityMapping getEntityMapping() {
        return entityMapping;
    }

    private Class<? extends Annotation> chooseAnnotationType(Method method, Set<Class<? extends Annotation>> types) {
        for (Class<? extends Annotation> type : types) {
            Annotation annotation = method.getAnnotation(type);
            if (annotation != null) {
                return type;
            }
        }
        return null;
    }

    private Class<? extends Annotation> getSqlEntityProviderAnnotationType(Method method) {
        return chooseAnnotationType(method, SQL_ENTITY_PROVIDER_ANNOTATION_TYPES);
    }

}
