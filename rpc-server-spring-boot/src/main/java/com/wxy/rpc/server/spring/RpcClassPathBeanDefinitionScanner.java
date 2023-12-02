package com.wxy.rpc.server.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 *
 * @Author: fcw
 * @Description: 类路径下的包扫描器
 * @Date: 2023-11-30   10:57
 */

// ClassPathBeanDefinitionScanner 是 Spring 提供的类路径下的包扫描器
public class RpcClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    // 这是一个泛型类型，表示可以持有任何继承自 Annotation 接口的类的 Class 对象
    private Class<? extends Annotation> annotationType;

    public RpcClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
        registerFilters();
    }

    public RpcClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotationType) {
        super(registry);
        this.annotationType = annotationType;
        // 放行指定的注解类型
        registerFilters();
    }

    /**
     * 注册过滤器，用来指定放行哪些类型
     */
    private void registerFilters() {
        // 放行指定 annotation 类型
        if (annotationType != null) {
            this.addIncludeFilter(new AnnotationTypeFilter(this.annotationType));
        } else { // 放行所有类型
            this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }
    }

    @Override
    // 它允许你在调用方法时传入任意数量的参数，这些参数会被自动组装成一个数组
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}
