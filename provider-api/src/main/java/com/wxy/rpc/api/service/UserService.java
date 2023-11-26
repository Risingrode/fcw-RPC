package com.wxy.rpc.api.service;

import com.wxy.rpc.api.pojo.User;

import java.util.List;

/**
 *
 * @Author: fcw
 * @Description:
 * @Date: 2023-11-25   23:02
 */

public interface UserService {

    User queryUser();

    List<User> getAllUsers();

}
