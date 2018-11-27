package com.xhh.springboot;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FreemarkerDemoController {

    @RequestMapping("index")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("name", "饶先龙");
        return "index";
    }



    public static void main(String[] args) throws IOException, TemplateException {

        Configuration conf = new Configuration();
        conf.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        conf.setObjectWrapper(new DefaultObjectWrapper());

        Template template = conf.getTemplate("index.ftl");

        //创建数据模型
        Map map = new HashMap();
        map.put("name", "饶先龙");

        //将模板和数据合并
        Writer out = new OutputStreamWriter(System.out);
        template.process(map , out);
        out.flush();
    }



}
