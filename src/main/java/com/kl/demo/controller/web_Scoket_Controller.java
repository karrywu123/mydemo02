package com.kl.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.kl.demo.webScoket.WebSocketServer;

/**
 * Created with IntelliJ IDEA.
 *
 * @ Auther: 马超伟
 * @ Date: 2020/06/16/14:38
 * @ Description:
 */
@Controller("web_Scoket_system")
@RequestMapping("/api/socket")
public class web_Scoket_Controller {
    //页面请求
    @GetMapping("/index/{userId}")
    public ModelAndView socket(@PathVariable String userId) {
        ModelAndView mav = new ModelAndView("/socket1");
        mav.addObject("userId", userId);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public Map pushToWeb(@PathVariable String cid, String message) {
        Map<String,Object> result = new HashMap<>();
        try {
            WebSocketServer.sendInfo(message, cid);
            result.put("code", cid);
            result.put("msg", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
