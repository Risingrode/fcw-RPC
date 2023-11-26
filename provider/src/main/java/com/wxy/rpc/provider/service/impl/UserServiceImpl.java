package com.wxy.rpc.provider.service.impl;

import com.wxy.rpc.api.pojo.User;
import com.wxy.rpc.api.service.UserService;
import com.wxy.rpc.server.annotation.RpcService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @Author: fcw
 * @Description:
 * @Date: 2023-11-25   22:59
 */

@RpcService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public User queryUser() {
        return new User("hwd", "123456", 25);
    }

    @Override
    public List<User> getAllUsers() {
        // 注意：直接使用 Arrays.ArrayList 会导致序列化异常
        // Arrays.asList` 返回的列表是不可修改的（immutable）列表。
        // 这意味着在这个列表上调用 `add`、`remove` 等修改操作会导致 `UnsupportedOperationException` 异常。
        return new ArrayList<>(Arrays.asList(new User("xm", "123456", 23),
                new User("hwd", "123456", 23),
                new User("hwd", "123456", 24)));
    }
}
