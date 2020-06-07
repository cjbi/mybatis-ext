package tech.wetech.mybatis.dialect;

import tech.wetech.mybatis.dialect.db.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 方言转换查找
 *
 * @author cjbi
 */
public class DialectClient {

    private static final Map<DialectType, Dialect> DBMS_DIALECT = new HashMap<>();

    public static Dialect getDialect(DialectType dialectType) {
        if (DBMS_DIALECT.containsKey(dialectType)) {
            return DBMS_DIALECT.get(dialectType);
        }
        Dialect dialect = createDialect(dialectType);
        DBMS_DIALECT.put(dialectType, dialect);
        return dialect;

    }

    private static Dialect createDialect(DialectType dialectType) {
        switch (dialectType) {
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
                return new OracleDialect();
            case DB2:
                return new DB2Dialect();
            case POSTGRE:
                return new PostgreSQLDialect();
            case H2:
                return new H2Dialect();
            case HSQL:
                return new HSQLDialect();
            case SQLSERVER:
                return new SQLServerDialect();
            case SQLSERVER2012:
                return new SqlServer2012Dialect();
            case DERBY:
                return new DerbyDialect();
            default:
                throw new UnsupportedOperationException("This database is not supported");
        }
    }

}
