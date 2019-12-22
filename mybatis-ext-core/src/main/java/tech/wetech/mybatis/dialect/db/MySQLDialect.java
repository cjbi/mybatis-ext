package tech.wetech.mybatis.dialect.db;

import tech.wetech.mybatis.dialect.Dialect;

/**
 * @author cjbi
 */
public class MySQLDialect implements Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        StringBuilder builder = new StringBuilder(sql.length() + 20).append(sql);
        if (offset > 0) {
            builder.append(String.format(" limit %s, %s", offset, limit));
        } else {
            builder.append(String.format(" limit %s", limit));
        }
        return builder.toString();
    }
}
