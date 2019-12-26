package tech.wetech.mybatis;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import tech.wetech.mybatis.dialect.Dialect;
import tech.wetech.mybatis.mapper.Mapper;


/**
 * 增强配置类
 *
 * @author cjbi
 */
public class ExtConfiguration extends Configuration {

    protected final EntityMapperRegistry entityMapperRegistry = new EntityMapperRegistry(this);

    protected Class<? extends Dialect> dialectClass = null;

    protected Dialect dialect = null;

    public ExtConfiguration() {
        super();
    }

    public Class<? extends Dialect> getDialectClass() {
        return dialectClass;
    }

    public Dialect getDialect() {
        if (dialectClass == null) {
            return null;
        }
        if (dialect == null) {
            try {
                dialect = dialectClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw ExceptionFactory.wrapException("Cannot get dialect instance.", e);
            }
        }
        return dialect;
    }

    public void setDialectClass(Class<? extends Dialect> dialectClass) {
        this.dialectClass = dialectClass;
    }

    public ExtConfiguration(Environment environment) {
        this();
        this.environment = environment;
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        entityMapperRegistry.addMapper(type);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        entityMapperRegistry.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        entityMapperRegistry.addMappers(packageName);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return entityMapperRegistry.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return type == Mapper.class || entityMapperRegistry.hasMapper(type);
    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        Executor executor = super.newExecutor(transaction, executorType);
        return new PagingExecutor(executor, this);
    }
}
