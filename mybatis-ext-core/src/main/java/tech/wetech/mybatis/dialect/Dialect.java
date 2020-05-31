package tech.wetech.mybatis.dialect;

/**
 * @author cjbi
 */
public interface Dialect {

    /**
     * 将sql转换为分页sql
     *
     * @param sql    sql语句
     * @param offset 偏移量
     * @param limit  分页数
     * @return 分页的sql
     */
    String getLimitString(String sql, int offset, int limit);

    /**
     * 将sql转换为总记录数sql
     *
     * @param sql sql语句
     * @return 总记录数的sql
     */
    default String getCountString(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ") tmp_count";
    }

}
