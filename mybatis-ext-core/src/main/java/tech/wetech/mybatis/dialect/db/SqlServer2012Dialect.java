package tech.wetech.mybatis.dialect.db;

import tech.wetech.mybatis.dialect.Dialect;

/**
 * @author cjbi
 */
public class SqlServer2012Dialect implements Dialect {
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 64);
        sqlBuilder.append(String.format(" offset %s rows fetch next %s rows only ", limit, offset));
        sqlBuilder.append(sql);
        return sqlBuilder.toString();
    }
}
