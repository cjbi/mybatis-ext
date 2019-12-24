package tech.wetech.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import tech.wetech.mybatis.dialect.DialectType;
import tech.wetech.mybatis.mapper.Mapper;


/**
 * 增强配置类
 *
 * @author cjbi
 */
public class ExtConfiguration extends Configuration {

    protected final EntityMapperRegistry entityMapperRegistry = new EntityMapperRegistry(this);

    protected DialectType defaultDialect = DialectType.MYSQL;

    public ExtConfiguration() {
        super();
    }

    public DialectType getDefaultDialect() {
        return defaultDialect;
    }

    public void setDefaultDialect(DialectType defaultDialect) {
        this.defaultDialect = defaultDialect;
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
        return new PageExecutor(executor, this);
    }
}
