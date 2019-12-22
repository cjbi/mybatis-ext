package tech.wetech.mybatis.dialect;

import tech.wetech.mybatis.example.Sort;
import tech.wetech.mybatis.util.EntityMappingUtil;

/**
 * @author cjbi
 */
public interface Dialect {

    /**
     * 将sql转换为分页sql
     *
     * @param sql
     * @param offset
     * @param limit
     * @return
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

    /**
     * 将sql转换为带排序的sql
     *
     * @param sql
     * @param className
     * @param sort
     * @return
     */
    default String getSortString(String sql, String className, Sort sort) {
        StringBuilder builder = new StringBuilder("SELECT * FROM (").append(sql).append(") tmp_order ORDER BY ");
        for (Sort.Order order : sort.getOrders()) {
            builder.append(EntityMappingUtil.getColumnName(className, order.getProperty()))
                    .append(" ")
                    .append(order.getDirection())
                    .append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        return builder.toString();
    }

}
