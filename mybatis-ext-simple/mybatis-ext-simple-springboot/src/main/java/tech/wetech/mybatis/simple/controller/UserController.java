package tech.wetech.mybatis.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.mybatis.simple.entity.User;
import tech.wetech.mybatis.simple.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/{userId}")
    private User queryUserById(@PathVariable("userId") Integer userId) {
        return userService.queryUserById(userId);
    }

}
