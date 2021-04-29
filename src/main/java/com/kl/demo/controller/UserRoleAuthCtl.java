package com.kl.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.kl.demo.domain.SysRole;
import com.kl.demo.domain.SysUser;
import com.kl.demo.domain.Ztree;
import com.kl.demo.logaop.AOPLog;
import com.kl.demo.service.SysAuthService;
import com.kl.demo.service.SysRoleService;
import com.kl.demo.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 涉及到账号、角色、权限的相关功能
 */
@Controller
@RequestMapping("/security")
public class UserRoleAuthCtl
{
    @Resource(name="sysUserService")
    private SysUserService sus;

    @Resource(name="sysRoleService")
    private SysRoleService srs;

    @Resource(name="sysAuthService")
    private SysAuthService sas;

    @RequestMapping("/ListUserRoleAuthHTML")
    public String findAllRole(Model model)
    {
        /**
         * 查询全部的角色，然后跳转到页面，利用了Thymeleaf模板中的迭代器进行页面数据的输出
         */
        List<SysRole> list=srs.findALL();
        model.addAttribute("sysRoles",list);
        return "security/ListUserRoleAuth.html";
    }

    @PostMapping("/listuser")
    @ResponseBody
    @AOPLog(operatetype="查询",operatedesc="浏览了一下账号列表")
    public String queryDynamic(@RequestBody Map<String,Object> reqMap)
    {
        /**
         * 系统账号分页查询
         */

        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"username"));

        Page<SysUser> pageinfo=sus.queryDynamic(reqMap, PageRequest.of(page,size,Sort.by(orders)));
        List<SysUser> sysUsers =pageinfo.getContent();
        JSONObject result = new JSONObject();
        result.put("rows", sysUsers);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }



    @RequestMapping("/listauth")
    @ResponseBody
    public List<Ztree> findALLToZtree(String roleid)
    {
        /**
         * 返回全部权限（zTree结构形式的树形节点）
         */
        return sas.findALLToZtree(roleid);
    }

    //保存角色
    @PostMapping("/saveRole")
    @ResponseBody
    @AOPLog(operatetype="新增",operatedesc="增加了一个角色")
    public String save(SysRole sysRole)
    {
        srs.save(sysRole);
        return "OK";
    }

    //删除角色
    @PostMapping("/deleteRole")
    @ResponseBody
    @AOPLog(operatetype="删除角色",operatedesc="删除了一个角色")
    public String deleteRole(String uuid)
    {
        srs.deleteByUuid(uuid);
        srs.deleteMaptabByUuid(uuid);
        return "OK";
    }

    //保存子节点（需要判断是否有重复，如果没有重复,保存信息,id是当前选定节点的id，name是需要新增加子节点的名称）
    @PostMapping("/saveChildAuth")
    @ResponseBody
    public String saveChildAuth(@RequestParam int id, String name)
    {
        return sas.saveChildAuth(id,name);
    }

    //根据节点的id删除节点及子节点
    @PostMapping("/deleteByChild")
    @ResponseBody
    public String deleteByChild(@RequestParam int id)
    {
        sas.deleteByChild(id);
        return "OK";
    }

    //保存角色对应的权限信息,其中‘authinfo’是以$分割的节点id字符串
    @PostMapping("/editRole")
    @ResponseBody
    public String editRole(@RequestParam String uuid,String authinfo)
    {
        sas.editRole(uuid,authinfo);
        return "OK";
    }

    //查询全部的角色(填充添加用户中的下拉列表)
    @RequestMapping("/findAllRoles")
    @ResponseBody
    public List<SysRole> findAllRoles()
    {
        List<SysRole> list=srs.findALL();
        return list;
    }

    //用户名唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateUsername")
    @ResponseBody
    public String validateUsername(@RequestParam String username)
    {
        boolean blStatus=sus.validateUsername(username);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //邮箱号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(@RequestParam String useremail)
    {
        boolean blStatus=sus.validateEmail(useremail);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //手机号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateMobile")
    @ResponseBody
    public String validateMobile(@RequestParam String usermobile)
    {
        boolean blStatus=sus.validateMobile(usermobile);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //返回AddSysUser.html页面
    @RequestMapping("/ridirectAddSysUserHtml")
    public String ridirectAddSysUserHtml()
    {
        return "security/AddSysUser.html";
    }


    //保存系统账户
    @RequestMapping("/saveSysUser")
    @ResponseBody
    @AOPLog(operatetype="新增",operatedesc="添加了一个系统账号")
    public String saveSysUser(SysUser sysUser)
    {
        sus.save(sysUser);
        return "OK";
    }

}
