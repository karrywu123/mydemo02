package com.kl.demo.security;

import com.kl.demo.domain.SysAuth;
import com.kl.demo.domain.SysUser;
import com.kl.demo.service.SysAuthService;
import com.kl.demo.service.SysRoleService;
import com.kl.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户加载类，用于验证用户，在验证通过的情况下，加载用户所对应的全部权限
 */
@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Resource(name = "sysUserService")
    private SysUserService sus;

    @Resource(name="sysAuthService")
    private SysAuthService sas;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        /**
         * 把我们自己定义账号转化为Spring Security体系内的账号
         */

        //根据账号名称、邮箱、手机号进行搜索（用的是JPA方式,参数是名称/邮箱/手机号之一），三者有其一，则验证通过
        SysUser sysUser=sus.findByUsernameOrUseremailOrUsermobile(s,s,s);
        if (sysUser==null)
        {
            throw new UsernameNotFoundException("用户名/密码错误");
        }

        //根据登录的用户，创建session,方便其他应用
        session.setAttribute("userinfo",sysUser);

        //获取该用户对应角色的权限，如果角色是‘超级管理员’，则直接获取全部的权限
        List<SysAuth> sysAuths=new ArrayList<SysAuth>();
        if(sysUser.getSysRole().getRolename().equals("超级管理员"))
        {
            sysAuths=sas.findAll();
        }
        else
        {
            for (SysAuth sysAuth:sysUser.getSysRole().getSysAuths())
            {
                sysAuths.add(sysAuth);
            }
        }

        //将权限信息添加到GrantedAuthority对象中，在后面进行权限验证时会使用GrantedAuthority对象
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (SysAuth sysAuth:sysAuths)
        {
            if(sysAuth!=null&&sysAuth.getFullname()!=null)
            {
                GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(sysAuth.getFullname());
                grantedAuthorities.add(grantedAuthority);
            }
        }

        return new User(sysUser.getUsername(),sysUser.getPassword(),grantedAuthorities);
    }
}
