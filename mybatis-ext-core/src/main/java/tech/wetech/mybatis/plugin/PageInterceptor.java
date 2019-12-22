package tech.wetech.mybatis.plugin;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author cjbi
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {

    /** mapped statement parameter index. */
    private static final int MAPPED_STATEMENT_INDEX = 0;
    /** parameter index. */
    private static final int PARAMETER_INDEX = 1;
    /** parameter index. */
    private static final int ROWBOUNDS_INDEX = 2;
    /** ResultHandler index. */
    private static final int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    private void processIntercept(final Object[] queryArgs) {
        //queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];

    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
