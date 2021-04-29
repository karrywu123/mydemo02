package com.kl.demo.service;

import com.kl.demo.domain.Company;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CompanyService
{
    //保存
    void save(Company company);

    //根据UUID删除记录
    void delete(String uuid);

    //修改
    @Transactional
    void update(Company company);

    //查询全部数据
    List<Company> findAll();

    //执行原生SQL语句的查询
    List<Company> findByNativeSQL(String companyname);

    //验证公司名称唯一性
    boolean validateComname(String comname);

    //验证邮箱唯一性
    boolean validateEmail(String email);

    //验证手机号唯一性
    boolean validateMobile(String mobile);

    //简单分页查询
    Page<Company> findAllSimplePage(Pageable pageable);

    //根据公司名称更新地址
    @Transactional
    void updateByName(String comaddress,String comname);

    //多条件动态查询
    Page<Company> queryDynamic(Map<String,Object> reqMap, Pageable pageable);

}