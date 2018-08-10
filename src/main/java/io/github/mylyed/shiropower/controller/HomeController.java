package io.github.mylyed.shiropower.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lilei on 2018/8/8.
 */
@Controller
public class HomeController {


    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Java欢迎您！！！";
    }


    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }


    @PostMapping("/subLogin")
    @ResponseBody
    public String subLogin(UsernamePasswordToken usernamePasswordToken) {
        System.out.println(usernamePasswordToken);
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
            return "登录成功";
        } catch (AuthenticationException e) {
            return "登录失败," + e.getMessage();
        }

    }


    @GetMapping("/403")
    public String unauthorized403() {
        return "403";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }


    //-------------


    @RequiresRoles("admin")
    @GetMapping("/testRole1")
    @ResponseBody
    public String testRole1() {
        return "testRole SUCCESS";
    }


    @RequiresRoles("admin2222")
    @GetMapping("/testRole2")
    @ResponseBody
    public String testRole2() {
        return "testRole SUCCESS";
    }


    @ResponseBody
    @GetMapping("/customFilter")
    public String customFilter() {
        return "customFilter";
    }

}
