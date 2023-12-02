package com.wxy.rpc.server.config;

import com.wxy.rpc.core.registry.ServiceRegistry;
import com.wxy.rpc.core.registry.nacos.NacosServiceRegistry;
import com.wxy.rpc.core.registry.zk.ZookeeperServiceRegistry;
import com.wxy.rpc.server.spring.RpcServerBeanPostProcessor;
import com.wxy.rpc.server.transport.RpcServer;
import com.wxy.rpc.server.transport.http.HttpRpcServer;
import com.wxy.rpc.server.transport.netty.NettyRpcServer;
import com.wxy.rpc.server.transport.socket.SocketRpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *
 * @Author: fcw
 * @Description: RpcServer 端的自动配置类
 * @Date: 2023-11-27   8:58
 */

// 被Bean注解标注的返回值会被注册到Spring容器中

@Configuration
//　允许通过 RpcServerProperties 类读取配置文件中的属性。
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcServerAutoConfiguration {

    @Autowired
    RpcServerProperties properties;

    /**
     * 创建 ServiceRegistry 实例 bean，当没有配置时默认使用 zookeeper 作为配置中心
     */
    @Bean(name = "serviceRegistry")
    //　当多个 ServiceRegistry 实例 bean 存在时，优先使用此 bean
    @Primary
    //　当容器中没有 ServiceRegistry 实例 bean 时，创建此 bean
    @ConditionalOnMissingBean
    // 当配置文件中 rpc.server.registry 属性值为 zookeeper 时，创建此 bean
    // matchIfMissing = true 的含义是，如果配置文件中没有 rpc.server.registry 属性，也视为条件匹配，即使用默认值
    // havingValue = "zookeeper" 的含义是，如果配置文件中 rpc.server.registry 属性值为 zookeeper，才视为条件匹配
    @ConditionalOnProperty(prefix = "rpc.server", name = "registry", havingValue = "zookeeper", matchIfMissing = true)
    public ServiceRegistry zookeeperServiceRegistry() {
        return new ZookeeperServiceRegistry(properties.getRegistryAddr());
    }

    @Bean(name = "serviceRegistry")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.server", name = "registry", havingValue = "nacos")
    public ServiceRegistry nacosServiceRegistry() {
        return new NacosServiceRegistry(properties.getRegistryAddr());
    }

    // 当没有配置通信协议属性时，默认使用 netty 作为通讯协议
    @Bean(name = "rpcServer")
    @Primary
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.server", name = "transport", havingValue = "netty", matchIfMissing = true)
    public RpcServer nettyRpcServer() {
        return new NettyRpcServer();
    }

    @Bean(name = "rpcServer")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.server", name = "transport", havingValue = "http")
    // 当 classpath 中存在 org.apache.catalina.startup.Tomcat 类时，创建此 bean
    @ConditionalOnClass(name = {"org.apache.catalina.startup.Tomcat"})
    public RpcServer httpRpcServer() {
        return new HttpRpcServer();
    }

    @Bean(name = "rpcServer")
    @ConditionalOnMissingBean
    // 不清楚 prefix 的具体作用，不知道会扫描哪个配置文件
    @ConditionalOnProperty(prefix = "rpc.server", name = "transport", havingValue = "socket")
    public RpcServer socketRpcServer() {
        return new SocketRpcServer();
    }

    @Bean
    @ConditionalOnMissingBean
    // 表示当Spring容器中存在ServiceRegistry和RpcServer类型的Bean时，这个Bean才会被创建
    @ConditionalOnBean({ServiceRegistry.class, RpcServer.class})
    public RpcServerBeanPostProcessor rpcServerBeanPostProcessor(@Autowired ServiceRegistry serviceRegistry,
                                                                 @Autowired RpcServer rpcServer,
                                                                 @Autowired RpcServerProperties properties) {

        //  容器启动时，将被 @RpcService 注解标注的类进行注册并暴露
        return new RpcServerBeanPostProcessor(serviceRegistry, rpcServer, properties);
    }

}
