package com.wxy.rpc.server.spring;

import com.wxy.rpc.server.annotation.RpcComponentScan;
import com.wxy.rpc.server.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

/**
 * @Author: fcw
 * @Description: Rpc 自定义服务的 BeanDefinition 注册器类
 * @Date: 2023-11-27   9:14
 */

@Slf4j
public class RpcBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    // 资源加载器 用于加载类路径下的资源 如 xml 配置文件 等
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 此方法会在 spring 自定义扫描执行之后执行，这个时候 beanDefinitionMap 已经有扫描到的 beanDefinition 对象了
     *
     * @param annotationMetadata annotation metadata of the importing class
     * @param registry           current bean definition registry
     */
    // 该方法自动执行哦
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        // 获取 RpcComponentScan 注解的属性和值 其中键是属性名，值是属性值
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(RpcComponentScan.class.getName()));
        String[] basePackages = {};
        if (annotationAttributes != null) {
            // 此处去获取RpcComponentScan 注解的 basePackages 值 basePackages 是一个string 数组
            basePackages = annotationAttributes.getStringArray("basePackages");
        }
        // 如果没有指定名称的话
        if (basePackages.length == 0) {
            // TODO 此处可以继续扩展，例如扫描指定类的包
            basePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }
        // 创建一个浏览 RpcService 注解的 Scanner
        // 备注：此处可以继续扩展，例如扫描 spring bean 或者其他类型的 Scanner
        // RpcClassPathBeanDefinitionScanner会扫描所有标注了RpcService注解的类
        // register是Spring容器中用于注册Bean定义的接口。在Spring容器启动时，所有的Bean定义都会被注册到这个接口中。
        RpcClassPathBeanDefinitionScanner rpcServiceScanner = new RpcClassPathBeanDefinitionScanner(registry, RpcService.class);

        if (this.resourceLoader != null) {
            rpcServiceScanner.setResourceLoader(this.resourceLoader);
        }

        // 扫描包下的所有 Rpc bean 并返回注册成功的数量（scan方法会调用register方法去注册扫描到的类并生成 BeanDefinition 注册到 spring 容器）
        int count = rpcServiceScanner.scan(basePackages);
        log.info("The number of BeanDefinition scanned and registered by RpcServiceScanner is {}.", count);
    }
}
