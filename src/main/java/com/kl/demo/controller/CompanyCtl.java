package com.kl.demo.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kl.demo.domain.Company;
import com.kl.demo.domain.EchartsData;
import com.kl.demo.service.CompanyService;
import com.kl.demo.utils.ReceiveUploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
//import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.*;

import org.springframework.web.client.RestTemplate;
import cn.hutool.http.HttpRequest;

@Controller
@RequestMapping("CompanyModule")
public class CompanyCtl
{
    //注入业务层实现类，如果一个业务层接口有多个实现类，则采用注解：@Qualifier，这里的“companyService”对应业务层实现类中定义的名称
    @Resource(name="companyService")
    private CompanyService cs;
    //测试POST刷新缓存请求
    private RestTemplate restTemplate;
    private static String POST_URL = "https://api.cloudflare.com/client/v4/zones/fd562e397b09e4e305a74a391c847bf1/purge_cache";

    //注入文件接收类
    @Autowired
    private ReceiveUploadFile receiveUploadFile;

    @PostMapping("save")
    @ResponseBody
    public void save(Company company)
    {
        /**
         * 接收表单，保存数据
         */
        cs.save(company);
    }

    @GetMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam String uuid)
    {
        /**
         * 根据接收到的uuid字符串，删除对应的数据库记录
         */
        System.out.println(uuid);
        cs.delete(uuid);
        //return "s";
    }
    public static String getType(Object o){ //获取变量类型方法
        return o.getClass().toString(); //使用int类型的getClass()方法
    }
    @GetMapping("/testGet_curl_jd855_cache")
    @ResponseBody
    public  String testGet_curl_jd855_cache() throws Exception
    {

        /**
         * 刷新缓存
         */
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Email","flying2958@gmail.com");
        headers.set("X-Auth-Key","7086e4ba9804417b4373a7e0858870880ab75");
        headers.set("Content-Type","application/json");
        JSONObject params=new JSONObject();
        params.put("purge_everything",true);

        HttpEntity httpEntity = new HttpEntity<>(params, headers);

        JSONObject result1 = new JSONObject();
        result1.put("purge_everything",true);
        // 发送post请求，并输出结果
        String result2 =HttpRequest.post(POST_URL)
                .header("X-Auth-Email","flying2958@gmail.com")
                .header("X-Auth-Key","7086e4ba9804417b4373a7e0858870880ab75")
                .header("Content-Type","application/json")
                .body(result1.toJSONString()).execute().body();
        System.out.println(result2);

        JSONObject jsonObject = restTemplate.postForObject(POST_URL, httpEntity, JSONObject.class);
        System.out.println(jsonObject.toJSONString());
        return result2;
    }
    @PostMapping("multiQuery")
    @ResponseBody
    public Map< String,Object> multiQuery(@RequestBody(required = false) Map<String,Object> reMap)
    {
        /***
         * 测试JSON格式的数据
         */
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String a1= (String) reMap.get("a1");
        String a2= (String) reMap.get("a2");
        String a3= (String) reMap.get("a3");
        String a4= (String) reMap.get("a4");
        System.out.println("当前时间为: " + ft.format(dNow)+"==>"+reMap);
        return reMap;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Company> findAll()
    {
        /**
         * 查询全部数据，get或post方式不限
         */
        return cs.findAll();
    }

    @PostMapping("/findAllSimplePage")
    @ResponseBody
    public Page<Company> findAllSimplePage(@RequestParam(name="page",required=false,defaultValue = "1") int page, @RequestParam(name="size",required=false,defaultValue = "2") int size)
    {
        /**
         * 多条件排序及分页查询
         */
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"comaddress"));

        return cs.findAllSimplePage(PageRequest.of(page,size,Sort.by(orders)));
    }

    @PostMapping("/findAllSimplePageMap")
    @ResponseBody
    public String findAllSimplePageMap(@RequestBody(required = false) Map<String,Object> reqMap)
    {
        /**
         * 简单分页查询
         */
        int page=0;
        int size=3;
        if(reqMap!=null)
        {
            if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
            if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"comaddress"));

        Page<Company> pageinfo=cs.findAllSimplePage(PageRequest.of(page,size,Sort.by(orders)));
        List<Company> companies =pageinfo.getContent();
        JSONObject result = new JSONObject();//maven中配置alibaba的fastjson依赖
        //"rows"和"total"这两个属性是为前端列表插件"bootstrap-table"服务的
        result.put("rows", companies);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }

    @PostMapping("queryDynamic")
    @ResponseBody
    public String queryDynamic(@RequestBody(required = false) Map<String,Object> reqMap)
    {
        /**
         * 多条件排序及多条件分页查询
         */
        int page=0;
        int size=3;
        if(reqMap!=null)
        {
            if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
            if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"comaddress"));

        Page<Company> pageinfo=cs.queryDynamic(reqMap,PageRequest.of(page,size,Sort.by(orders)));
        List<Company> companies =pageinfo.getContent();
        JSONObject result = new JSONObject();//maven中配置alibaba的fastjson依赖
        //"rows"和"total"这两个属性是为前端列表插件"bootstrap-table"服务的
        result.put("rows", companies);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }


    @RequestMapping("findByNativeSQL")
    @ResponseBody
    public List<Company> findByNativeSQL(@RequestParam String companyname)
    {
        return cs.findByNativeSQL(companyname);
    }

    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(@RequestParam String contactemail)
    {
        /**
         * 验证邮箱是否唯一，唯一:{"valid":true},不唯一：{"valid"：false}
         */
        boolean blStatus=cs.validateEmail(contactemail);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    @PostMapping("upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file)
    {
        /**
         * 接收上传文件，反馈回服务器端的文件保存路径（全路径）
         */
        return receiveUploadFile.receiveFile(file,"myfile");
    }

    @PostMapping("chart")
    @ResponseBody
    public Map<String,List<EchartsData>> chart()
    {
        /**
         * 为Echarts图表展示，准备数据
         * 这里反馈回回聊员工数量、营业收入两个集合，并赋值给map集合
         */
        Map<String,List<EchartsData>> map = new HashMap<>();

        List<EchartsData> listEmployeenumber=new ArrayList<EchartsData>();//数据集合，员工数量
        List<EchartsData> listTotaloutput=new ArrayList<EchartsData>();//数据集合，营业收入
        List<Company> list=cs.findAll();

        // 组装数据
        for (Company company:list)
        {
            listEmployeenumber.add(new EchartsData(company.getComname(),company.getEmployeenumber()));
            listTotaloutput.add(new EchartsData(company.getComname(),company.getTotaloutput()));
        }

        //赋值给Map集合 KEY,VALUE
        map.put("listperson",listEmployeenumber);
        map.put("listoutput",listTotaloutput);

        return map;
    }



    /*****************************************************
     * 通过控制层访问访问页面，分安全文件夹templates、公共文件夹public两种情况
     ****************************************************/
    @RequestMapping("showHtml")
    public String showHtml()
    {
        /**
         * templates文件夹是安全文件夹，在该文件夹下的页面文件（Themeleaf模板或者普通html页面）不能直接访问，只能通过控制层访问，
         * 注意返回的是mydemo.html这个Thymeleaf模板，.html后缀可以不带，不过为了明显，建议大家还是带上
         */
        return "report/CompanyReport.html";
    }

    @RequestMapping("showCP")
    public String showCommonPage()
    {
        /**
         * 非Templates文件夹下的页面跳转，一般就是普通的公共页面（可以直接访问的，一般都是非Thymeleaf模板页面），如果需要在控制层中跳转，则需添加‘redirect:’前缀
         * 资源文件中，resources、static、public这三个文件夹可以直接访问，而templates文件夹是安全文件夹，不能直接访问
         */
        return "redirect:/demo.html";
    }

    @RequestMapping("showIndex")
    public String showIndexPage()
    {
        /**
         * 访问利用公共页面的抽取页面
         */
        return "first.html";
    }

    @RequestMapping("listCompany")
    public String listCompany()
    {
        /**
         * 返回客户列表页面
         */
        return "company/ListCompany.html";
    }

    @RequestMapping("addCompanyHtml")
    public String addCompanyHtml()
    {
        /**
         * 返回新增公司页面
         */
        return "company/AddCompany.html";
    }

    @RequestMapping("chartHtml")
    public String chartHtml()
    {
        /**
         * 返回公司图表展示页面
         */
        return "company/ChartCompany.html";
    }


    /*****************************************************
     * Restful设计风格
     ****************************************************/
    @GetMapping("/company")
    @ResponseBody
    public List<Company> findAllRF()
    {
        /**
         * 查询数据，等于@RequestMapping(value="/company",method = RequestMethod.GET)
         */
        return cs.findAll();
    }

    @GetMapping("/company/{comname}")
    @ResponseBody
    public List<Company> findByNativeSQLRT(@PathVariable String comname)
    {
        /**
         * 单参数查询
         */
        return cs.findByNativeSQL(comname);
    }

    @PutMapping("/company/{comaddress}/{comname}")
    @ResponseBody
    public void updateByNameRT(@PathVariable String comaddress,@PathVariable String comname)
    {
        /**
         * 多参数，更新数据
         */
        System.out.println(comaddress);
        System.out.println(comname);
        cs.updateByName(comaddress,comname);
    }



}