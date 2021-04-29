package com.kl.demo.service.impl;

import com.kl.demo.domain.SysRole;
import com.kl.demo.domain.SysUser;
import com.kl.demo.repository.SysRoleRepo;
import com.kl.demo.repository.SysUserRepo;
import com.kl.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 账号业务层实现类
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService
{
    @Autowired
    private SysUserRepo sysUserRepo;

    @Autowired
    private SysRoleRepo sysRoleRepo;

    //新添加用户
    @Override
    public void save(SysUser sysUser)
    {
        //密码进行BCrypt强哈希加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String hashPassword=passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(hashPassword);

        //根据账号对应的角色id（sysroleid），得到角色信息
        SysRole sysRole=sysRoleRepo.findByUuid(sysUser.getSysroleid());
        sysUser.setSysrolename(sysRole.getRolename());
        sysUser.setSysRole(sysRole);

        sysUserRepo.save(sysUser);
    }

    @Override
    public SysUser findByUsernameOrUseremailOrUsermobile(String username, String email, String mobile)
    {
        return sysUserRepo.findByUsernameOrUseremailOrUsermobile(username,email,mobile);
    }

    //带查询条件的分页查询
    @Override
    public Page<SysUser> queryDynamic(Map<String,Object> reqMap, Pageable pageable)
    {
        Specification querySpecifi=new Specification<SysUser>()
        {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if(!(reqMap.get("username")==null||reqMap.get("username").toString().equals("")))//账号名称，like 模糊查询
                {
                    predicates.add(criteriaBuilder.like(root.get("username"),"%"+reqMap.get("username").toString()+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return this.sysUserRepo.findAll(querySpecifi,pageable);
    }

    //查询对应的账号名称是否存在（服务层用于唯一性验证）,如果用户已经存在返回false，否则返回true
    @Override
    public boolean validateUsername(String username)
    {
        int intCount=sysUserRepo.validateUsername(username);
        if(intCount==0){return true;}
        else{return false;}
    }

    //邮箱号唯一性验证(如果已经存在，返回false，否则返回true)
    @Override
    public boolean validateEmail(String email)
    {
        int intCount=sysUserRepo.validateEmail(email);
        if(intCount==0){return true;}
        else{return false;}
    }

    //手机号唯一性验证(如果已经存在，返回false，否则返回true)
    @Override
    public boolean validateMobile(String mobile)
    {
        int intCount=sysUserRepo.validateMobile(mobile);
        if(intCount==0){return true;}
        else{return false;}
    }


}
