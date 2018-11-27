package com.xhh.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspDemoController {

    @RequestMapping("jIndex")
    public String jspDemo(ModelMap modelMap){
        modelMap.addAttribute("name","饶先龙");
        return "jIndex";
    }


}
