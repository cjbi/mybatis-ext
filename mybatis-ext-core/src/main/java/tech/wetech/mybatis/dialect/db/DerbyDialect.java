package tech.wetech.mybatis.dialect.db;

import tech.wetech.mybatis.dialect.Dialect;

public class DerbyDialect implements Dialect {
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return String.format(sql + " OFFSET %s ROWS FETCH NEXT %s ROWS ONLY ", offset, limit);
    }
}
