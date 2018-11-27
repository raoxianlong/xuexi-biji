package com.xhh.springboot;

import com.xhh.service.JdbcDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootDemoController {

    @Autowired
    JdbcDemoService jdbcDemoService;

    @RequestMapping("/hello")
    public String hello(@RequestParam String name){
        return name + "， 你好";
    }

    @RequestMapping("/exception")
    public String exception() throws Exception {
        throw new Exception("sdfdsfds");
    }
    @RequestMapping("/jdbc")
    public String jdbcDemo(){
        jdbcDemoService.updateJdbc();
        return "你好，添加成功,快去看看!";
    }


}
