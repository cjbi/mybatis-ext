package tech.wetech.mybatis.simple.service;

import tech.wetech.mybatis.simple.entity.User;

public interface UserService {

    User queryUserById(Integer userId);

}
