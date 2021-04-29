package com.kl.demo.repository;

import com.kl.demo.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 账号数据仓库接口
 */
public interface SysUserRepo extends JpaRepository<SysUser, String>, JpaSpecificationExecutor
{
    //使用SpringDataJPA方法定义查询,根据用户名/邮件/手机号，查询用户信息
    SysUser findByUsernameOrUseremailOrUsermobile(String username,String email,String mobile);

    //账号唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from sysuser where username=?1",nativeQuery = true)
    int validateUsername(String username);

    //邮箱号唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from sysuser where useremail=?1",nativeQuery = true)
    int validateEmail(String email);

    //手机号唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from sysuser where usermobile=?1",nativeQuery = true)
    int validateMobile(String mobile);


}
