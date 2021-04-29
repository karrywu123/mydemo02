package com.kl.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *自定义安全配置类，访问任何url之前进行验证
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{
    @Bean
    UserDetailsService UDS(){return new MyUserDetailsService();}

    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception
    {
        /**
         * 配置认证管理器AuthenticationManager
         */
        amb.userDetailsService(UDS()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /**
         * 配置安全过滤器HttpSecurity
         */
        http.csrf().disable();// 关闭csrf防护
        http
                .authorizeRequests()
                .antMatchers("/jscustom/**","/jslib/**","/druid/**").permitAll()     // 允许访问的静态资源，注意这里的根目录是指public/static/templates
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()//允许iframe嵌套（springSecurty使用X-Frame-Options防止网页被Frame，默认是deny，拒绝iframe嵌套）
                .and()
                .formLogin()
                .loginPage("/login")       // 设置登录页面（这里不是页面，而是登录控制类中相应的路径，下同）
                .successForwardUrl("/index") //登录成功后forward到参数指定的url
                .failureUrl("/login")      //登录失败后就forward到参数指定的url
                .permitAll()
                .and() // 注销成功跳转路径url
                .logout()
                .permitAll();

        http.sessionManagement()//配置session的功能实现单点登录
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)//强制让之前登录的同一账号被挤出去
                .expiredUrl("/login");//session过期后跳转的url
        http.headers().contentTypeOptions().disable();//设置允许加载静态资源文件,如css
    }

}

