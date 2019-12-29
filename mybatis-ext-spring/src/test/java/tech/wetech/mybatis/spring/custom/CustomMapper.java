package tech.wetech.mybatis.spring.custom;

import org.apache.ibatis.annotations.Select;
import tech.wetech.mybatis.mapper.BaseMapper;

import java.util.List;

public interface CustomMapper<T> extends BaseMapper<T> {
    @Select("select * from weshop_user where username=#{username}")
    List<T> selectByUsername(String username);
}
