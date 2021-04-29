package com.kl.demo.controller;


import com.kl.demo.logaop.AOPLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录相关的控制层
 */
@Controller
public class LoginCtl
{
    @RequestMapping(value="/login")
    public String login()
    {
        /**
         * 返回登录页面
         */
        return "login.html";
    }

    @RequestMapping(value = "/index")
    @AOPLog(operatetype="登录",operatedesc="用户登录后台系统")
    public String index()
    {
        /**
         * 返回首页
         */
        return "index.html";
    }
    @RequestMapping(value = "/logout")
    @AOPLog(operatetype="退出登录",operatedesc="用户退出登录后台系统")
    public String logout(HttpServletRequest request)
    {
        /**
         * 退出登陆
         */
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }
}