package tech.wetech.mybatis.mapper;

import org.apache.ibatis.jdbc.SQL;
import tech.wetech.mybatis.ExtConfiguration;
import tech.wetech.mybatis.builder.EntityMapping;

import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public class BaseEntitySqlBuilder extends AbstractEntityProvider {

    public String deleteByPrimaryKey(EntityMapping entityMapping) {
        if (entityMapping.isLogicDelete()) {
            return new SQL() {{
                UPDATE(entityMapping.getTableName());
                SET(String.format("%s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteDeletedValue()));
                WHERE(String.format("%s = #{%s}", entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
            }}.toString();
        }
        return new SQL() {{
            DELETE_FROM(entityMapping.getTableName());
            WHERE(String.format("%s = #{%s}", entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
        }}.toString();
    }

    public String existsByPrimaryKey(EntityMapping entityMapping) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM(entityMapping.getTableName());
            WHERE(String.format("%s = #{%s}", entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
            if (entityMapping.isLogicDelete()) {
                WHERE(String.format("%s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
            }
        }}.toString();
    }


    public String updateByExampleSelective(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder setBuilder = new StringBuilder("<set>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            if (entityMapping.isOptimisticLock() && entityMapping.getOptimisticLockProperty().equals(columnProperty.getPropertyName())) {
                setBuilder.append(String.format("%s = %s + 1,", entityMapping.getOptimisticLockColumn(), entityMapping.getOptimisticLockColumn()));
                continue;
            }
            setBuilder.append(String.format("<if test='record.%s != null'>%s = #{record.%s},</if>", columnProperty.getPropertyName(), columnProperty.getColumnName(), columnProperty.getPropertyName()));
        }
        builder.append("<bind name='oredCriteria' value='example.oredCriteria'/>");
        setBuilder.append("</set>");
        builder.append(String.format("UPDATE %s %s %s", entityMapping.getTableName(), setBuilder, buildExampleXML(configuration, entityMapping)));
        builder.append("</script>");
        return builder.toString();
    }


    public String updateByExample(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder setBuilder = new StringBuilder("<set>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            if (entityMapping.isOptimisticLock() && entityMapping.getOptimisticLockProperty().equals(columnProperty.getPropertyName())) {
                setBuilder.append(String.format("%s = %s + 1,", entityMapping.getOptimisticLockColumn(), entityMapping.getOptimisticLockColumn()));
                continue;
            }
            setBuilder.append(String.format("%s = #{record.%s},", columnProperty.getColumnName(), columnProperty.getPropertyName()));
        }
        setBuilder.append("</set>");
        builder.append("<bind name='oredCriteria' value='example.oredCriteria'/>");
        builder.append(String.format("UPDATE %s %s %s", entityMapping.getTableName(), setBuilder, buildExampleXML(configuration, entityMapping)));
        builder.append("</script>");
        return builder.toString();
    }


    public String deleteByExample(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format("UPDATE %s", entityMapping.getTableName()));
            builder.append(String.format(" SET %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteDeletedValue()));
        } else {
            builder.append(String.format("DELETE FROM %s", entityMapping.getTableName()));
        }
        builder.append(buildExampleXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String countByExample(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("SELECT COUNT(*) FROM %s", entityMapping.getTableName()));
        builder.append(buildExampleXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String selectByExample(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("select %s from %s", buildExampleColumnsXML(configuration, entityMapping), entityMapping.getTableName()));
        builder.append(buildExampleXML(configuration, entityMapping));
        builder.append(String.format("<if test='orderByClause != null'> order by ${orderByClause}</if>"));
        builder.append("</script>");
        return builder.toString();
    }


    public String count(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("SELECT COUNT(*) FROM %s ", entityMapping.getTableName()));
        builder.append(buildWhereNotNullXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String selectOneWithOptional(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("SELECT %s FROM %s ", buildAllColumns(configuration, entityMapping), entityMapping.getTableName()));
        builder.append(buildWhereNotNullXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String selectOne(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("SELECT %s FROM %s ", buildAllColumns(configuration, entityMapping), entityMapping.getTableName()));
        builder.append(buildWhereNotNullXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String selectList(ExtConfiguration configuration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        builder.append(String.format("SELECT %s FROM %s ", buildAllColumns(configuration, entityMapping), entityMapping.getTableName()));
        builder.append(buildWhereNotNullXML(configuration, entityMapping));
        builder.append("</script>");
        return builder.toString();
    }


    public String selectAll(ExtConfiguration configuration, EntityMapping entityMapping) {
        return new SQL() {{
            SELECT(buildAllColumns(configuration, entityMapping));
            FROM(entityMapping.getTableName());
            if (entityMapping.isLogicDelete()) {
                WHERE(String.format("%s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
            }
        }}.toString();
    }


    public String updateByPrimaryKeySelective(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder setBuilder = new StringBuilder("<set>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            if (entityMapping.isOptimisticLock() && entityMapping.getOptimisticLockProperty().equals(columnProperty.getPropertyName())) {
                setBuilder.append(String.format("%s = %s + 1,", columnProperty.getColumnName(), columnProperty.getColumnName()));
                continue;
            }
            setBuilder.append(String.format("<if test='%s != null'>%s = #{%s},</if>", columnProperty.getPropertyName(), columnProperty.getColumnName(), columnProperty.getPropertyName()));
        }
        setBuilder.append("</set>");
        builder.append(String.format("UPDATE %s %s WHERE %s = #{%s}", entityMapping.getTableName(), setBuilder, entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format(" and %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
        }
        if (entityMapping.isOptimisticLock()) {
            builder.append(String.format("<if test='%s != null'> and %s = #{%s}</if>", entityMapping.getOptimisticLockProperty(), entityMapping.getOptimisticLockColumn(), entityMapping.getOptimisticLockProperty()));
        }
        builder.append("</script>");
        return builder.toString();
    }


    public String updateByPrimaryKey(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder setBuilder = new StringBuilder("<set>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            if (entityMapping.isOptimisticLock() && entityMapping.getOptimisticLockProperty().equals(columnProperty.getPropertyName())) {
                setBuilder.append(String.format("%s = %s + 1,", entityMapping.getOptimisticLockColumn(), entityMapping.getOptimisticLockColumn()));
                continue;
            }
            setBuilder.append(String.format("%s = #{%s},", columnProperty.getColumnName(), columnProperty.getPropertyName()));
        }
        setBuilder.append("</set>");
        builder.append(String.format("UPDATE %s %s WHERE %s = #{%s}", entityMapping.getTableName(), setBuilder, entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format(" and %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
        }
        if (entityMapping.isOptimisticLock()) {
            builder.append(String.format("<if test='%s != null'> and %s = #{%s}</if>", entityMapping.getOptimisticLockProperty(), entityMapping.getOptimisticLockColumn(), entityMapping.getOptimisticLockProperty()));
        }
        builder.append("</script>");
        return builder.toString();
    }


    public String selectByPrimaryKeyWithOptional(ExtConfiguration configuration, EntityMapping entityMapping) {
        return new SQL() {{
            SELECT(buildAllColumns(configuration, entityMapping));
            FROM(entityMapping.getTableName());
            WHERE(String.format("%s = #{%s}", entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
            if (entityMapping.isLogicDelete()) {
                WHERE(String.format("%s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
            }
        }}.toString();
    }


    public String selectByPrimaryKey(ExtConfiguration configuration, EntityMapping entityMapping) {
        return new SQL() {{
            SELECT(buildAllColumns(configuration, entityMapping));
            FROM(entityMapping.getTableName());
            WHERE(String.format("%s = #{%s}", entityMapping.getKeyColumn(), entityMapping.getKeyProperty()));
            if (entityMapping.isLogicDelete()) {
                WHERE(String.format("%s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
            }
        }}.toString();
    }

    public String insertSelective(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder columnsBuilder = new StringBuilder("<trim prefix='(' suffix=')' suffixOverrides=','>");
        StringBuilder valuesBuilder = new StringBuilder("<trim prefix='(' suffix=')' suffixOverrides=','>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            columnsBuilder.append(String.format("<if test='%s != null'>%s,</if>", columnProperty.getPropertyName(), columnProperty.getColumnName()));
            valuesBuilder.append(String.format("<if test='%s != null'>#{%s},</if>", columnProperty.getPropertyName(), columnProperty.getPropertyName()));
        }
        columnsBuilder.append("</trim>");
        valuesBuilder.append("</trim>");
        builder.append(String.format("INSERT INTO %s %s VALUES%s", entityMapping.getTableName(), columnsBuilder, valuesBuilder));
        builder.append("</script>");
        return builder.toString();
    }


    public String insertAll(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder columnsBuilder = new StringBuilder("(");
        columnsBuilder.append(entityMapping.getColumnProperties().stream().map(c -> String.format("%s", c.getColumnName())).collect(Collectors.joining(", ")));
        columnsBuilder.append(")");
        StringBuilder valueBuilder = new StringBuilder("<foreach collection='list' item='item' index='index' separator=','>");
        valueBuilder.append("(");
        valueBuilder.append(entityMapping.getColumnProperties().stream().map(c -> String.format("#{item.%s}", c.getPropertyName())).collect(Collectors.joining(", ")));
        valueBuilder.append(")");
        valueBuilder.append("</foreach>");
        builder.append(String.format("INSERT INTO %s %s VALUES%s", entityMapping.getTableName(), columnsBuilder, valueBuilder));
        builder.append("</script>");
        return builder.toString();
    }


    public String insert(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder("<script>");
        StringBuilder columnsBuilder = new StringBuilder("<trim prefix='(' suffix=')' suffixOverrides=','>");
        StringBuilder valuesBuilder = new StringBuilder("<trim prefix='(' suffix=')' suffixOverrides=','>");
        for (EntityMapping.ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            columnsBuilder.append(columnProperty.getColumnName()).append(" , ");
            valuesBuilder.append("#{").append(columnProperty.getPropertyName()).append("}").append(" , ");
        }
        columnsBuilder.append("</trim>");
        valuesBuilder.append("</trim>");
        builder.append(String.format("INSERT INTO %s %s VALUES%s", entityMapping.getTableName(), columnsBuilder, valuesBuilder));
        builder.append("</script>");
        return builder.toString();
    }
}
