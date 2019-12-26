package tech.wetech.mybatis;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import tech.wetech.mybatis.dialect.Dialect;
import tech.wetech.mybatis.dialect.DialectClient;
import tech.wetech.mybatis.dialect.DialectType;
import tech.wetech.mybatis.domain.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cjbi
 */
public class PagingExecutor implements Executor {

    private final Executor delegate;
    private final ExtConfiguration configuration;

    private static final Log LOG = LogFactory.getLog(PagingExecutor.class);

    public int getTotalCount(
            final MappedStatement mappedStatement, final Object parameterObject,
            final BoundSql boundSql, Dialect dialect) throws SQLException {
        final String count_sql = dialect.getCountString(boundSql.getSql());
        LOG.debug("Total count SQL: " + count_sql);
        if (parameterObject != null) {
            LOG.debug("Total count Parameters: " + parameterObject);
        }
        Connection connection = this.getTransaction().getConnection();
        PreparedStatement countStmt = connection.prepareStatement(count_sql);
        DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        handler.setParameters(countStmt);

        ResultSet rs = countStmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        LOG.debug("Total count: " + count);
        return count;
    }

    public PagingExecutor(Executor delegate, ExtConfiguration configuration) {
        this.delegate = delegate;
        this.configuration = configuration;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return delegate.update(ms, parameter);
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
                                      String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private Dialect getAutoDialect() {
        try {
            String url = this.getTransaction().getConnection().getMetaData().getURL();
            for (DialectType dialectType : DialectType.values()) {
                if (url.toUpperCase().indexOf(String.format(":%s:", dialectType)) != -1) {
                    return DialectClient.getDialect(dialectType);
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    private Page getPage(Object parameter) {
        Page page = ParametersFinder.getInstance().findParameter(parameter, Page.class);
        if (page == null && ThreadContext.getPage() != null) {
            try {
                page = ThreadContext.getPage();
            } finally {
                ThreadContext.removeAll();
            }
        }
        return page;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        Page page = getPage(parameter);
        if (page != null) {
            //优先使用配置的数据库方言
            Dialect dialect = configuration.getDialectClass() != null ? configuration.getDialect() : getAutoDialect();
            int totalCount = 0;
            if (page.isCountable()) {
                try {
                    totalCount = getTotalCount(ms, parameter, boundSql, dialect);
                } catch (SQLException e) {
                    LOG.error("Total count error: ", e);
                }
                if (totalCount == 0) {
                    return page;
                }
            }
            String sql = boundSql.getSql();
            String limitSql = dialect.getLimitString(sql, page.getOffset(), page.getPageSize());
            BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, limitSql);
            List<E> list = delegate.query(ms, parameter, rowBounds, resultHandler, cacheKey, newBoundSql);
            page.addAll(list);
            page.setTotal(totalCount);
            return page;
        }
        return delegate.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        CacheKey cacheKey = delegate.createCacheKey(ms, parameter, rowBounds, boundSql);
        return query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        return delegate.queryCursor(ms, parameter, rowBounds);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return delegate.flushStatements();
    }

    @Override
    public void commit(boolean required) throws SQLException {
        delegate.commit(required);
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        delegate.rollback(required);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return delegate.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return delegate.isCached(ms, key);
    }

    @Override
    public void clearLocalCache() {
        delegate.clearLocalCache();
    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {
        delegate.deferLoad(ms, resultObject, property, key, targetType);
    }

    @Override
    public Transaction getTransaction() {
        return delegate.getTransaction();
    }

    @Override
    public void close(boolean forceRollback) {
        delegate.close(forceRollback);
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public void setExecutorWrapper(Executor executor) {
        delegate.setExecutorWrapper(executor);
    }
}
