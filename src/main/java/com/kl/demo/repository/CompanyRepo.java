package com.kl.demo.repository;

import com.kl.demo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company, String>, JpaSpecificationExecutor
{
    //原生sql语句查询
    @Query(value="select * from company where comname=?1",nativeQuery = true)
    List<Company> findByNativeSQL(String comname);

    @Query(value="select * from company where comname like '%?1%'",nativeQuery = true)
    List<Company> findByNativeSQL1(String comname);

    //原生sql语句操作（涉及到数据变动的，如删除和更新，必须加注解@Modifying）
    @Modifying
    @Query(value = "update company set comaddress =?1 where comname=?2",nativeQuery = true)
    void updateByName(String comaddress,String comname);

    //公司唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from company where comname=?1",nativeQuery = true)
    int validateComname(String username);

    //邮箱号唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from company where contactemail=?1",nativeQuery = true)
    int validateEmail(String email);

    //手机号唯一性验证(如果已经存在，返回0，否则返回1)
    @Query(value = "select count(*) from company where contactmobile=?1",nativeQuery = true)
    int validateMobile(String mobile);
}