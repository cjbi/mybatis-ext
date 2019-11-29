package tech.wetech.mybatis.spring.custom;

import tech.wetech.mybatis.mapper.BaseMapper;

import java.util.List;

public interface CustomMapper<T> extends BaseMapper<T, Integer> {
    List<T> selectByUsername(String username);
}
