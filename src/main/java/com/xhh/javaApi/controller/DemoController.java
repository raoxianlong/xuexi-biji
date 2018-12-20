package com.xhh.javaApi.controller;

import annotation.Controller;
import annotation.RequestMapping;

@Controller
@RequestMapping("/demo/")
public class DemoController {


    @RequestMapping("/list")
    public String list(String name, String flag){


        return "list";
    }


}
