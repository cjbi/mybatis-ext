package tech.wetech.mybatis.mapper;

import tech.wetech.mybatis.builder.EntityMapping;
import tech.wetech.mybatis.builder.EntityMapping.ColumnProperty;

import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public abstract class AbstractEntityProvider {

    protected String buildAllColumns(EntityMapping entityMapping) {
        return entityMapping.getColumnProperties().stream()
                .map(ColumnProperty::getColumnName)
                .collect(Collectors.joining(", "));
    }

    protected String buildExampleColumnsXML(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder();
        builder.append("<if test='distinct'> distinct </if> ");
        builder.append("<choose>");
        builder.append("<when test='columns != null'>");
        builder.append(String.format("<foreach collection='columns' separator=', ' item='item'>${item}</foreach>", entityMapping.getEntityClass().getName()));
        builder.append("</when>");
        builder.append("<when test='columns == null'>");
        builder.append(buildAllColumns(entityMapping));
        builder.append("</when>");
        builder.append("</choose>");
        return builder.toString();
    }

    protected String buildExampleXML(EntityMapping entityMapping) {
        String className = entityMapping.getEntityClass().getName();
        StringBuilder builder = new StringBuilder();
        builder.append("<where>");
        builder.append("<foreach collection='oredCriteria' item='criteria'>");
        builder.append("<if test='criteria.valid'>");
        builder.append(" ${criteria.andOr} ");
        builder.append("<trim prefix='(' prefixOverrides='and|or' suffix=')'>");
        builder.append("<foreach collection='criteria.criteria' item='criterion'>");
        builder.append("<choose>");
        builder.append("<when test='criterion.noValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.singleValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} #{criterion.value}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.betweenValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.listValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} ", className));
        builder.append("<foreach collection='criterion.value' item='id' index='index' open='(' close=')' separator=', '>#{id}</foreach>");
        builder.append("</when>");
        builder.append("</choose>");
        builder.append("</foreach>");
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format(" and %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
        }
        builder.append("</trim>");
        builder.append("</if>");
        builder.append("</foreach>");
        builder.append("</where>");
        return builder.toString();
    }

    protected String buildWhereNotNullXML(EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder();
        builder.append("<where>");
        for (ColumnProperty columnProperty : entityMapping.getColumnProperties()) {
            builder.append(String.format("<if test='%s != null'> AND %s = #{%s}</if>", columnProperty.getPropertyName(), columnProperty.getColumnName(), columnProperty.getPropertyName()));
        }
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format(" and %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
        }
        builder.append("</where>");
        return builder.toString();
    }

}
