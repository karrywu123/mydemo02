package com.kl.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.kl.demo.domain.Systemlog;
import com.kl.demo.service.SystemlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("SystemlogModule")
public class SystemlogCtl
{
    @Resource(name="systemlogService")
    private SystemlogService ss;

    @RequestMapping("listSystemlogHTML")
    public String showIndexPage()
    {
        /**
         * 访问利用公共页面的抽取页面
         */
        return "systemlog/ListSystemlog.html";
    }

    @PostMapping("queryDynamic")
    @ResponseBody
    public String queryDynamic(@RequestBody(required = false) Map<String,Object> reqMap)
    {
        /**
         * 多条件排序及多条件分页查询
         */
        int page = 0;
        int size = 3;
        if (reqMap != null) {
            if (reqMap.get("page").toString() != null) {
                page = Integer.parseInt(reqMap.get("page").toString());
            }
            if (reqMap.get("size").toString() != null) {
                size = Integer.parseInt(reqMap.get("size").toString());
            }
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "operatetime"));

        Page<Systemlog> pageinfo = ss.queryDynamic(reqMap, PageRequest.of(page, size, Sort.by(orders)));
        List<Systemlog> systemlogs = pageinfo.getContent();
        JSONObject result = new JSONObject();//maven中配置alibaba的fastjson依赖
        //"rows"和"total"这两个属性是为前端列表插件"bootstrap-table"服务的
        result.put("rows", systemlogs);
        result.put("total", pageinfo.getTotalElements());
        return result.toJSONString();
    }

}
