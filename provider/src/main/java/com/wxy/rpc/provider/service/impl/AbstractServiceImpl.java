package com.wxy.rpc.provider.service.impl;

import com.wxy.rpc.api.service.AbstractService;
import com.wxy.rpc.server.annotation.RpcService;

/**
 *
 * @Author: fcw
 * @Description:
 * @Date: 2023-11-25   22:58
 */

@RpcService(interfaceClass = AbstractService.class)
public class AbstractServiceImpl extends AbstractService {
    @Override
    public String abstractHello(String name) {
        return "abstract hello " + name;
    }
}
