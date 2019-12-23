package tech.wetech.mybatis.mapper;

import tech.wetech.mybatis.builder.EntityMapperBuilder;
import tech.wetech.mybatis.builder.EntityMapping;
import tech.wetech.mybatis.builder.EntityMapping.ColumnProperty;
import tech.wetech.mybatis.ExtConfiguration;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public abstract class AbstractEntityProvider {

    public static String columnName(String className, String property) {
        EntityMapping entityMapping = EntityMapperBuilder.TABLE_ENTITY_CACHE.get(className);
        Map<String, ColumnProperty> columnPropertyMap = entityMapping.getColumnPropertyMap();
        ColumnProperty columnProperty = columnPropertyMap.get(property);
        return columnProperty.getColumnName();
    }

    protected String buildAllColumns(ExtConfiguration extConfiguration, EntityMapping entityMapping) {
        return entityMapping.getColumnProperties().stream()
                .map(ColumnProperty::getColumnName)
                .collect(Collectors.joining(", "));
    }

    protected String buildExampleColumnsXML(ExtConfiguration extConfiguration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder();
        builder.append("<if test='distinct'> distinct </if> ");
        builder.append("<choose>");
        builder.append("<when test='columns != null'>");
        builder.append(String.format("<foreach collection='columns' separator=', ' item='item'>${item}</foreach>", entityMapping.getEntityClass().getName()));
        builder.append("</when>");
        builder.append("<when test='columns == null'>");
        builder.append(buildAllColumns(extConfiguration, entityMapping));
        builder.append("</when>");
        builder.append("</choose>");
        return builder.toString();
    }

    protected String buildCriteriaXML(ExtConfiguration extConfiguration, EntityMapping entityMapping) {
        String className = entityMapping.getEntityClass().getName();
        StringBuilder builder = new StringBuilder();
        builder.append("<trim prefix='where' prefixOverrides='and|or'>");
        builder.append("<foreach collection='criteria.criterions' item='item'>");
        builder.append("<if test='item.noValue'>");
        builder.append(String.format(" ${item.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',item.property)} ${item.condition}", className));
        builder.append("</if>");
        builder.append("<if test='item.singleValue'>");
        builder.append(String.format(" ${item.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',item.property)} ${item.condition} #{item.value}", className));
        builder.append("</if>");
        builder.append("<if test='item.betweenValue'>");
        builder.append(String.format(" ${item.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',item.property)} ${item.condition} #{item.value} and #{item.secondValue}", className));
        builder.append("</if>");
        builder.append("<if test='item.listValue'>");
        builder.append(String.format(" ${item.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',item.property)} ${item.condition} ", className));
        builder.append("<foreach collection='item.value' item='id' index='index' open='(' close=')' separator=', '>#{id}</foreach>");
        builder.append("</if>");
        builder.append("</foreach>");
        if (entityMapping.isLogicDelete()) {
            builder.append(String.format(" and %s = %s", entityMapping.getLogicDeleteColumn(), entityMapping.getLogicDeleteNormalValue()));
        }
        builder.append("</trim>");
        return builder.toString();
    }

    protected String buildWhereNotNullXML(ExtConfiguration extConfiguration, EntityMapping entityMapping) {
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
