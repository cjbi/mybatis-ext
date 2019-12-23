package tech.wetech.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import tech.wetech.mybatis.dialect.DBMS;
import tech.wetech.mybatis.dialect.Dialect;
import tech.wetech.mybatis.mapper.Mapper;


/**
 * 增强配置类
 *
 * @author cjbi
 */
public class ExtConfiguration extends Configuration {

    protected final EntityMapperRegistry entityMapperRegistry = new EntityMapperRegistry(this);

    protected boolean pageTotalCountEnabled = true;

    protected DBMS dbms = DBMS.MYSQL;

    public ExtConfiguration() {
        super();
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
        if (pageTotalCountEnabled) {
            return new PageExecutor(executor, this);
        }
        return executor;
    }

    public void setDbms(DBMS dbms) {
        this.dbms = dbms;
    }

    public Dialect getDialect() {
        return DBMS.getDbmsDialect(dbms);
    }

    public void setPageTotalCountEnabled(boolean pageTotalCountEnabled) {
        this.pageTotalCountEnabled = pageTotalCountEnabled;
    }
}
