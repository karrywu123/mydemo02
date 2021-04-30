这个工程的实例数据和运行的步骤
创建数据库和用户授权 
create database spring default charset utf8;

grant all  on  spring.* to spring@'%' IDENTIFIED by '123456';

grant all on spring.* to 'spring'@'%' identified by '123456' with grant option; 

flush privileges;




INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40283f8178fd0f6c0178fd1382bd0000', '广州市越秀区广园西路342号一楼109、二楼203房', '阿里妈妈商务旅游咨询服务（广州）有限公司', '阿里妈妈商务旅游咨询服务', '正常运营', '010-888889999', 'http://alimamamlvyou.com', '18028682273@163.com', '13654897555', '阿里妈妈旅游', 4000, '2002-09', 128000.0000, '');
INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40288bac7867a59e017867aecdc30002', 'DGGG', 'qwqwqwqwq', 'DGGG', '正常运营', '0979330816', 'DGGG', '123456@qq.com', 'wqwqwq', 'wqwqetwerrwe', 13242, 'DGGG', 30000.2227, 'wqwqw');
INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40288bac7867a59e017867c858a70003', 'DGGG', 'qwqwqwqwq', 'DGGG', '正在注销', '0979330816', 'DGGG', '1234567@qq.com', 'wqwqwq', 'wqwqetwerrwe', 13242, 'DGGG', 10000.0000, 'wqwqw');
INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40288bac78a7f3f20178a805187b0000', '广州番禺区科珠路302', '专业技术大数据分析', '大数据分析公司', '正常运营', '0777777', 'www.dashujkooo.com', '123456789@qq.com', '1373333394', '猪泰迪', 12000, '2002030', 20000.2207, '凄凄切切');
INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40288bac78b7a2a20178b7a57d170000', '广州天河区体育中心', '正在运营电商公司', '广州阿里妈妈公司', '正常运营', '016566265', 'http://alimamam.com', '14789@qq.com', '13654897644', '阿里妈妈', 3000, '2006-09', 128000.0000, '');
INSERT INTO `company`(`uuid`, `comaddress`, `comdesc`, `comname`, `comstatus`, `comtelephone`, `comurl`, `contactemail`, `contactmobile`, `contactname`, `employeenumber`, `establishdate`, `totaloutput`, `upload`) VALUES ('40288bac78d12f950178d13218cd0000', '广州天河区体育中心正佳广场', '腾讯copy公司', '广州腾讯妈妈公司', '正常运营', '010-888888888', 'http://tencentmam.com', '1314@qq.com', '13879231454', '腾讯妈妈', 4000, '2002-06', 228000.0000, '');


INSERT INTO `sysuser`(`uuid`, `password`, `sysroleid`, `sysrolename`, `useremail`, `usermobile`, `username`) VALUES ('40283f817901ec58017901f4938e0013', '$2a$10$ZNiQFGjPQ9koJq5H0DERVu1uZtLbN1iyHSysPMVWXrcjWZGYYugWy', '40283f8178f7d4a50178f7d8e85d0000', '超级管理员', 'kl@gmail.com', '13215354694', 'kl');
INSERT INTO `sysuser`(`uuid`, `password`, `sysroleid`, `sysrolename`, `useremail`, `usermobile`, `username`) VALUES ('40283f81790314de017903173ca90009', '$2a$10$k06e.h3MxDPu5InSGMsOj.XI4h0WW0amb94lTYQprUSa2eRIURWj6', '40283f8178f7d4a50178f7d946120001', '普通用户', 'alony@gmail.com', '13215354698', 'alony');

INSERT INTO `sysrole`(`uuid`, `roledesc`, `rolename`) VALUES ('40283f8178f7d4a50178f7d8e85d0000', '超级管理员', '超级管理员');
INSERT INTO `sysrole`(`uuid`, `roledesc`, `rolename`) VALUES ('40283f8178f7d4a50178f7d946120001', '普通用户', '普通用户');
INSERT INTO `sysrole`(`uuid`, `roledesc`, `rolename`) VALUES ('40283f817901ec58017901f5eea50014', '普通员工', '普通员工');

INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901eed7560007', '系统管理', 3, 0, '系统管理');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901ef57f20008', '系统管理_账号角色权限', 31, 3, '账号角色权限');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901efdbcb0009', '系统管理_账号角色权限_新增账号', 311, 31, '新增账号');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f0155d000a', '系统管理_账号角色权限_删除账号', 312, 31, '删除账号');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f046de000b', '系统管理_账号角色权限_修改账号', 313, 31, '修改账号');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f1ba5a000c', '系统管理_Druid数据库连接池', 32, 3, 'Druid数据库连接池');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f2553d000d', '系统管理_账号角色权限_新增角色', 314, 31, '新增角色');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f2930b000e', '系统管理_账号角色权限_删除角色', 315, 31, '删除角色');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f2d0d5000f', '系统管理_账号角色权限_分配权限', 316, 31, '分配权限');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f342b40010', '系统管理_账号角色权限_新增一级权限', 317, 31, '新增一级权限');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f393ec0011', '系统管理_账号角色权限_新增子权限', 318, 31, '新增子权限');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901ec58017901f3cbbc0012', '系统管理_账号角色权限_删除权限', 319, 31, '删除权限');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a017901ff23ce0000', '公司管理', 4, 0, '公司管理');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a017901ff5ff30001', '公司管理_公司列表', 41, 4, '公司列表');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a017901ff7efd0002', '公司管理_公司图表', 42, 4, '公司图表');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a01790200d1b50003', '公司管理_公司列表_新增公司', 411, 41, '新增公司');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a0179020104630004', '公司管理_公司列表_修改公司', 412, 41, '修改公司');
INSERT INTO `sysauth`(`uuid`, `fullname`, `id`, `pid`, `treename`) VALUES ('40283f817901fb4a017902012f740005', '公司管理_公司列表_删除公司', 413, 41, '删除公司');

INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901ec58017901eed7560007');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901ec58017901ef57f20008');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901ec58017901f1ba5a000c');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a017901ff23ce0000');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a017901ff5ff30001');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a01790200d1b50003');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a0179020104630004');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a017902012f740005');
INSERT INTO `sysrole_sys_auths`(`sys_role_uuid`, `sys_auths_uuid`) VALUES ('40283f8178f7d4a50178f7d946120001', '40283f817901fb4a017901ff7efd0002');




flush privileges;


在启动文件加上
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

这段代码

如下
package com.kl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}



在pom.xml
注释 这段

        <!--thymeleaf基于Spring security的标签-->
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
 
注释UserRoleAuthCtl.java
这两段

@PostMapping("/listuser")
@ResponseBody
//@AOPLog(operatetype="查询",operatedesc="浏览了一下账号列表")  注释这里
public String queryDynamic(@RequestBody Map<String,Object> reqMap)
{


//保存系统账户
@RequestMapping("/saveSysUser")
@ResponseBody
//@AOPLog(operatetype="新增",operatedesc="添加了一个系统账号") 注释这里
public String saveSysUser(SysUser sysUser)
    {
        sus.save(sysUser);
        return "OK";
    }

}




后面重新启动工程

创建超级用户  

创建好超级用户后
把刚刚注释好的都还原

就可以用超级用户登录了
