package controller;

import annotation.Controller;
import annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/admin/emp")
public class DemoController {

    @RequestMapping("add")
    public String add(Emp emp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("name=" + emp.getName());
        System.out.println("age=" + emp.getAge());
        request.setAttribute("name" ,emp.getName());
        request.setAttribute("age" ,emp.getAge());
        return "/admin/list";
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "/admin/insert";
    }



}
