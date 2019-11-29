package tech.wetech.mybatis.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.mybatis.simple.entity.User;
import tech.wetech.mybatis.simple.mapper.UserMapper;

/**
 * @author cjbi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserById(Integer userId) {
        return userMapper.selectByPrimaryKeyWithOptional(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}
