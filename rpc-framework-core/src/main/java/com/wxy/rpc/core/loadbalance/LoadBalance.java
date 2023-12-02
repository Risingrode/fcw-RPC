package com.wxy.rpc.core.loadbalance;

import com.wxy.rpc.core.common.RpcRequest;
import com.wxy.rpc.core.common.ServiceInfo;
import com.wxy.rpc.core.extension.SPI;

import java.util.List;

/**
 *
 * @Author: fcw
 * @Description: 负载均衡 接口类
 * @Date: 2023-12-02   18:22
 */

@SPI
public interface LoadBalance {

    /**
     * 负载均衡，从传入的服务列表中按照指定的策略返回一个
     *
     * @param invokers 服务列表
     * @param request rpc请求
     * @return 按策略返回的服务信息对象
     */
    // 这个函数在AbstractLoadBalance 里面实现了，一致性哈希负载均衡算法
    ServiceInfo select(List<ServiceInfo> invokers, RpcRequest request);

}
