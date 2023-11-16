package com.wxy.rpc.consumer.controller;

import com.wxy.rpc.api.pojo.User;
import com.wxy.rpc.api.service.UserService;
import com.wxy.rpc.client.annotation.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wuxy
 * @version 1.0
 * @ClassName UserController
 * @Date 2023/1/8 23:47
 */
@RestController
public class UserController {

    @RpcReference
    private UserService userService;

    // queryUser() 是api包里面的UserService, 然后这个service要与impl连接，这个impl是 provider里面的
    @RequestMapping("/user/getUser")
    public User getUser() {

        return userService.queryUser();
    }

    @RequestMapping("/user/getAllUser")
    public List<User> getAllUser() {

        return userService.getAllUsers();
    }

}
