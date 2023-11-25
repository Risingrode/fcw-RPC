package com.wxy.rpc.consumer.controller;

import com.wxy.rpc.api.service.AbstractService;
import com.wxy.rpc.api.service.HelloService;
import com.wxy.rpc.client.annotation.RpcReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fcw
 * @Description:
 * @Date: 2023-11-23   17:49
 */

@RestController
@RequestMapping
public class HelloController {

    // 这个注解是自定义的 client包里面的
    @RpcReference
    // api包里面的
    private HelloService helloService;

    @RpcReference
    private AbstractService abstractService;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        return helloService.sayHello(name);
    }

    // 这个就返回个json数据
    @RequestMapping("/hello/test/{count}")
    public Map<String, Long> performTest(@PathVariable("count") Long count) {
        Map<String, Long> result = new HashMap<>();
        result.put("调用次数", count);
        long start = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            helloService.sayHello(Long.toString(i));
        }
        result.put("耗时", System.currentTimeMillis() - start);
        // System.out.println(result);
        return result;
    }

    @RequestMapping("/abstracthello/{name}")
    public String abstractHello(@PathVariable("name") String name) {
        return abstractService.abstractHello(name);
    }


}
