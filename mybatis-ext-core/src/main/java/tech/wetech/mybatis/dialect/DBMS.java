package tech.wetech.mybatis.dialect;

import tech.wetech.mybatis.dialect.db.DB2Dialect;
import tech.wetech.mybatis.dialect.db.MySQLDialect;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型
 *
 * @author cjbi
 */
public enum DBMS {
    MYSQL,
    ORACLE,
    DB2,
    H2,
    HSQL,
    POSTGRE,
    SQLSERVER,
    SQLSERVER2005,
    SYBASE;

    private static final Map<DBMS, Dialect> DBMS_DIALECT = new HashMap<>();

    public static Dialect getDbmsDialect(DBMS dbms) {
        if (DBMS_DIALECT.containsKey(dbms)) {
            return DBMS_DIALECT.get(dbms);
        }
        Dialect dialect = createDbmsDialect(dbms);
        DBMS_DIALECT.put(dbms, dialect);
        return dialect;
    }

    private static Dialect createDbmsDialect(DBMS dbms) {
        switch (dbms) {
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
//                return new OracleDialect();
            case DB2:
                return new DB2Dialect();
            case POSTGRE:
//                return new PostgreSQLDialect();
            case SQLSERVER:
//                return new SQLServerDialect();
            case SQLSERVER2005:
//                return new SQLServer2005Dialect();
            case SYBASE:
//                return new SybaseDialect();
            case H2:
//                return new H2Dialect();
            case HSQL:
//                return new HSQLDialect();
            default:
                throw new UnsupportedOperationException("Empty dbms dialect");
        }
    }

}
