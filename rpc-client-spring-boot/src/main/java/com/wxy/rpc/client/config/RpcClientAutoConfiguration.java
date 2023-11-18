package com.wxy.rpc.client.config;

import com.wxy.rpc.client.spring.RpcClientBeanPostProcessor;
import com.wxy.rpc.client.proxy.ClientStubProxyFactory;
import com.wxy.rpc.client.spring.RpcClientExitDisposableBean;
import com.wxy.rpc.client.transport.RpcClient;
import com.wxy.rpc.client.transport.http.HttpRpcClient;
import com.wxy.rpc.client.transport.netty.NettyRpcClient;
import com.wxy.rpc.client.transport.socket.SocketRpcClient;
import com.wxy.rpc.core.discovery.ServiceDiscovery;
import com.wxy.rpc.core.discovery.nacos.NacosServiceDiscovery;
import com.wxy.rpc.core.discovery.zk.ZookeeperServiceDiscovery;
import com.wxy.rpc.core.loadbalance.impl.ConsistentHashLoadBalance;
import com.wxy.rpc.core.loadbalance.LoadBalance;
import com.wxy.rpc.core.loadbalance.impl.RandomLoadBalance;
import com.wxy.rpc.core.loadbalance.impl.RoundRobinLoadBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * RpcClient 自动配置类
 * <pre>
 *     1. ConditionalOnBean：是否存在某个某类或某个名字的Bean
 *     2. ConditionalOnMissingBean：是否缺失某个某类或某个名字的Bean
 *     3. ConditionalOnSingleCandidate：是否符合指定类型的Bean只有⼀个
 *     4. ConditionalOnClass：是否存在某个类
 *     5. ConditionalOnMissingClass：是否缺失某个类
 *     6. ConditionalOnExpression：指定的表达式返回的是true还是false
 *     7. ConditionalOnJava：判断Java版本
 *     8. ConditionalOnJndi：JNDI指定的资源是否存在
 *     9. ConditionalOnWebApplication：当前应⽤是⼀个Web应⽤
 *     10. ConditionalOnNotWebApplication：当前应⽤不是⼀个Web应⽤
 *     11. ConditionalOnProperty：Environment中是否存在某个属性
 *     12. ConditionalOnResource：指定的资源是否存在
 *     13. ConditionalOnWarDeployment：当前项⽬是不是以War包部署的⽅式运⾏
 *     14. ConditionalOnCloudPlatform：是不是在某个云平台上
 * </pre>
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName RpcClientAutoConfiguration
 * @Date 2023/1/8 12:06
 */

/*
    1. 根据配置创建不同的负载均衡策略的 Bean（Random、RoundRobin、ConsistentHash）。
    2. 根据配置创建不同的服务发现的 Bean（Zookeeper、Nacos）。
    3. 根据配置创建不同的 RPC 客户端的 Bean（Netty、Http、Socket）。
    4. 创建客户端代理工厂、Bean后置处理器以及在应用退出时销毁资源的 Bean。
*/

@Configuration
//  告诉 Spring Boot 将 RpcClientProperties 类注册为配置属性类，以便在其他地方通过 @Autowired 注解来注入这个配置类的实例，并使用其中的属性值。
@EnableConfigurationProperties(RpcClientProperties.class)
public class RpcClientAutoConfiguration {

    /**
     * 属性绑定的实现方式二：
     * - 创建 RpcClientProperties 对象，绑定到配置文件
     * - 如果使用此方法，可以直接给属性赋初始值
     *
     * @param environment 当前应用的环境（支持 yaml、properties 等文件格式）
     * @return 返回对应的绑定属性类
     * @deprecated 弃用，使用被 {@link org.springframework.boot.context.properties.ConfigurationProperties} 标注的属性类代替，
     * 生成 metadata。
     */
    @Deprecated
    public RpcClientProperties rpcClientProperties(Environment environment) {
        // 获取绑定器，将对应的属性绑定到指定类上
        BindResult<RpcClientProperties> bind = Binder.get(environment).bind("rpc.client", RpcClientProperties.class);
        // 获取实例
        return bind.get();
    }

    @Autowired
    RpcClientProperties rpcClientProperties;

    // 声明该方法返回一个 Bean
    @Bean(name = "loadBalance")
    // 在多个同类型的 Bean 中，优先选择该 Bean。
    @Primary
    // 当容器中不存在同类型的 Bean 时创建这个 Bean。
    @ConditionalOnMissingBean // 不指定 value 则值默认为当前创建的类
    // 当配置文件中属性 rpc.client.loadbalance 的值为 "random" 时，创建这个 Bean。
    // matchIfMissing = true 表示如果未配置该属性，则同样创建这个 Bean。
    @ConditionalOnProperty(prefix = "rpc.client", name = "loadbalance", havingValue = "random", matchIfMissing = true)
    public LoadBalance randomLoadBalance() {
        return new RandomLoadBalance();
    }

    @Bean(name = "loadBalance")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "loadbalance", havingValue = "roundRobin")
    public LoadBalance roundRobinLoadBalance() {
        return new RoundRobinLoadBalance();
    }

    @Bean(name = "loadBalance")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "loadbalance", havingValue = "consistentHash")
    public LoadBalance consistentHashLoadBalance() {
        return new ConsistentHashLoadBalance();
    }

    @Bean(name = "serviceDiscovery")
    @Primary
    @ConditionalOnMissingBean
    @ConditionalOnBean(LoadBalance.class)
    @ConditionalOnProperty(prefix = "rpc.client", name = "registry", havingValue = "zookeeper", matchIfMissing = true)
    public ServiceDiscovery zookeeperServiceDiscovery(@Autowired LoadBalance loadBalance) {
        return new ZookeeperServiceDiscovery(rpcClientProperties.getRegistryAddr(), loadBalance);
    }

    @Bean(name = "serviceDiscovery")
    @ConditionalOnMissingBean
    @ConditionalOnBean(LoadBalance.class)
    @ConditionalOnProperty(prefix = "rpc.client", name = "registry", havingValue = "nacos")
    public ServiceDiscovery nacosServiceDiscovery(@Autowired LoadBalance loadBalance) {
        return new NacosServiceDiscovery(rpcClientProperties.getRegistryAddr(), loadBalance);
    }

    @Bean(name = "rpcClient")
    @Primary
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "transport", havingValue = "netty", matchIfMissing = true)
    public RpcClient nettyRpcClient() {
        return new NettyRpcClient();
    }

    @Bean(name = "rpcClient")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "transport", havingValue = "http")
    public RpcClient httpRpcClient() {
        return new HttpRpcClient();
    }

    @Bean(name = "rpcClient")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "transport", havingValue = "socket")
    public RpcClient socketRpcClient() {
        return new SocketRpcClient();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ServiceDiscovery.class, RpcClient.class})
    public ClientStubProxyFactory clientStubProxyFactory(@Autowired ServiceDiscovery serviceDiscovery,
                                                         @Autowired RpcClient rpcClient,
                                                         @Autowired RpcClientProperties rpcClientProperties) {
        return new ClientStubProxyFactory(serviceDiscovery, rpcClient, rpcClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcClientBeanPostProcessor rpcClientBeanPostProcessor(@Autowired ClientStubProxyFactory clientStubProxyFactory) {
        return new RpcClientBeanPostProcessor(clientStubProxyFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcClientExitDisposableBean rpcClientExitDisposableBean(@Autowired ServiceDiscovery serviceDiscovery) {
        return new RpcClientExitDisposableBean(serviceDiscovery);
    }

}
