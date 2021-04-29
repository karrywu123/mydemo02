package com.kl.demo.service;

import com.kl.demo.domain.SysRole;

import java.util.List;

/**
 * 角色业务层接口
 */
public interface SysRoleService {
    //根据uuid查找角色
    SysRole findByUuid(String uuid);

    //查询全部的权限
    List<SysRole> findALL();

    //保存角色
    void save(SysRole sysRole);

    //根据uuid，删除角色
    void deleteByUuid(String uuid);

    //根据角色uuid，删除角色权限关联表中角色对应的记录
    void deleteMaptabByUuid(String uuid);
}
