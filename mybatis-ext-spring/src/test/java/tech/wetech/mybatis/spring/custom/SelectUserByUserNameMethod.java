package tech.wetech.mybatis.spring.custom;

import org.apache.ibatis.mapping.SqlCommandType;
import tech.wetech.mybatis.builder.EntityMapping;
import tech.wetech.mybatis.mapper.AbstractEntityProvider;
import tech.wetech.mybatis.ExtConfiguration;

public class SelectUserByUserNameMethod extends AbstractEntityProvider {

    public String script(ExtConfiguration extConfiguration, EntityMapping entityMapping) {
        StringBuilder builder = new StringBuilder();
        builder.append("<script>");
        builder.append(String.format("select %s from %s where username=#{username}", "*", entityMapping.getTableName()));
        builder.append("</script>");
        return builder.toString();
    }

    public String methodName() {
        return null;
    }

    public SqlCommandType sqlCommandType() {
        return null;
    }

    public Class<?> resultType() {
        return null;
    }
}
