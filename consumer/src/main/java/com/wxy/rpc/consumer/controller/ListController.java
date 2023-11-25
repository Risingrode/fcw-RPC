package com.wxy.rpc.consumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
//@RestController
public class ListController {
    // 下面是视图解析器测试内容
    // 11-25日，17：25 运行成功！！！
    @GetMapping("/list/ViewTest/{name}")
    public String viewTest(@PathVariable(value = "name") String name, Model model) {
        model.addAttribute("message", "hello,world  " + name);
        return "list";
    }


//    @GetMapping("/list/ViewTest/{name}")
//    public ModelAndView index(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("list");
//        modelAndView.addObject("message", "hello,world"+name);
//        return modelAndView;
//    }


}
