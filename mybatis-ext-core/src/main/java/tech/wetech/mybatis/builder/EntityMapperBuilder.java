package tech.wetech.mybatis.builder;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import tech.wetech.mybatis.ExtConfiguration;
import tech.wetech.mybatis.mapper.Mapper;
import tech.wetech.mybatis.util.EntityMappingUtil;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * 自定义的单表Crud查询Builder类
 *
 * @author cjbi
 */
public class EntityMapperBuilder {

    private final ExtConfiguration configuration;
    private final MapperBuilderAssistant assistant;
    private final Class<?> type;
    public final static Map<String, EntityMapping> TABLE_ENTITY_CACHE = new HashMap<>();

    public EntityMapperBuilder(ExtConfiguration configuration, Class<?> type) {
        this.configuration = configuration;
        this.type = type;
        String resource = type.getName().replace('.', '/') + ".java (best guess)";
        this.assistant = new MapperBuilderAssistant(configuration, resource);
    }

    public void parse() {
        String resource = type.toString();
        if (!configuration.isResourceLoaded(resource)) {
            if (Mapper.class.isAssignableFrom(type)) {
                Stream.of(type.getMethods()).forEach(this::parseStatement);
            }
            loadAnnotationResource();
        }
    }

    private void parseStatement(Method method) {
        Class<?> entityClass = EntityMappingUtil.extractEntityClass(type);
        if (entityClass == null) {
            return;
        }
        EntityMapping entityMapping = TABLE_ENTITY_CACHE.get(entityClass.getName());
        if (entityMapping == null) {
            entityMapping = new EntityMappingBuilder(entityClass).build();
            TABLE_ENTITY_CACHE.put(entityClass.getName(), entityMapping);
        }
        EntityMapperAnnotationResolver annotationResolver = new EntityMapperAnnotationResolver(method, configuration, entityMapping);
        if (!annotationResolver.isEntityMethodProvider()) {
            return;
        }

        LanguageDriver languageDriver = configuration.getLanguageDriver(XMLLanguageDriver.class);
        assistant.setCurrentNamespace(type.getName());
        final String mappedStatementId = type.getName() + "." + method.getName();
        String resultMapId = mappedStatementId + "#resultMap";
        String script = annotationResolver.getScript();
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, entityClass);
        applyResultMap(resultMapId, annotationResolver, entityMapping);
        ResultSetType resultSetType = configuration.getDefaultResultSetType();
        SqlCommandType sqlCommandType = annotationResolver.getSqlCommandType();
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean useCache = isSelect;
        KeyGenerator keyGenerator;
        String keyProperty = entityMapping.getKeyProperty();
        if (sqlCommandType == SqlCommandType.INSERT) {
            keyGenerator = Jdbc3KeyGenerator.INSTANCE;
            if (method.getParameterCount() > 1) {
                ParamNameResolver paramNameResolver = new ParamNameResolver(configuration, method);
                Type[] resolveParamTypes = TypeParameterResolver.resolveParamTypes(method, type);
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    if ("T".equals(resolveParamTypes[i].getTypeName())) {
                        keyProperty = paramNameResolver.getNames()[i] + "." + entityMapping.getKeyProperty();
                        break;
                    }
                }
            }
        } else {
            keyGenerator = NoKeyGenerator.INSTANCE;
        }
        assistant.addMappedStatement(mappedStatementId, sqlSource, StatementType.PREPARED, sqlCommandType,
                null, null, null, entityClass, resultMapId, entityClass,
                resultSetType, flushCache, useCache, false, keyGenerator, keyProperty, entityMapping.getKeyColumn(),
                configuration.getDatabaseId(), languageDriver, null);
    }

    private void loadAnnotationResource() {
        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(configuration, type);
        parser.parse();
    }

    private void applyResultMap(String resultMapId, EntityMapperAnnotationResolver resolver, EntityMapping entityMapping) {
        Method method = resolver.getMethod();
        Class<?> resultType = getReturnType(method, resolver);
        List<ResultMapping> resultMappings = new ArrayList<>();
        if (resultType == resolver.getEntityMapping().getEntityClass()) {
            resultType = entityMapping.getEntityClass();
            for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
                ResultMapping.Builder builder = new ResultMapping.Builder(configuration, columnProperty.getPropertyName(), columnProperty.getColumnName().replace("`", ""), columnProperty.getJavaType());
                resultMappings.add(builder.build());
            }
        }
        assistant.addResultMap(resultMapId, resultType, null, null, resultMappings, null);
    }

    private Class<?> getReturnType(Method method, EntityMapperAnnotationResolver resolver) {
        Class<?> returnType = method.getReturnType();
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, type);
        if ("T".equals(resolvedReturnType.getTypeName())) {
            returnType = resolver.getEntityMapping().getEntityClass();
        }
        if (resolvedReturnType instanceof Class) {
            returnType = (Class<?>) resolvedReturnType;
            if (returnType.isArray()) {
                returnType = returnType.getComponentType();
            }
            // gcode issue #508
            if (void.class.equals(returnType)) {
                ResultType rt = method.getAnnotation(ResultType.class);
                if (rt != null) {
                    returnType = rt.value();
                }
            }
        } else if (resolvedReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType) || Cursor.class.isAssignableFrom(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (gcode issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (gcode issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    } else if ("T".equals(returnTypeParameter.getTypeName())) {
                        returnType = resolver.getEntityMapping().getEntityClass();
                    }
                }
            } else if (method.isAnnotationPresent(MapKey.class) && Map.class.isAssignableFrom(rawType)) {
                // (gcode issue 504) Do not look into Maps if there is not MapKey annotation
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 2) {
                    Type returnTypeParameter = actualTypeArguments[1];
                    if (returnTypeParameter instanceof Class<?>) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (gcode issue 443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    }
                }
            } else if (Optional.class.equals(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type returnTypeParameter = actualTypeArguments[0];
                if (returnTypeParameter instanceof Class<?>) {
                    returnType = (Class<?>) returnTypeParameter;
                } else if ("T".equals(returnTypeParameter.getTypeName())) {
                    returnType = resolver.getEntityMapping().getEntityClass();
                }
            }
        }

        return returnType;
    }

}
