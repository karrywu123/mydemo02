package com.kl.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kl.demo.domain.SysAuth;
import com.kl.demo.domain.SysRole;
import com.kl.demo.domain.Ztree;
import com.kl.demo.repository.SysAuthRepo;
import com.kl.demo.repository.SysRoleRepo;
import com.kl.demo.service.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限业务层实现类
 */
@Service("sysAuthService")
public class SysAuthServiceImpl implements SysAuthService
{
    @Autowired
    private SysAuthRepo sysAuthRepo;

    @Autowired
    private SysRoleRepo sysRoleRepo;

    @Override
    public List<SysAuth> findAll()
    {
        return sysAuthRepo.findAll();
    }

    @Override
    public List<Ztree> findALLToZtree(String roleid)
    {
        List<Ztree> listztree=new ArrayList<Ztree>();

        //根据角色uuid获取角色对应的权限
        SysRole sysRole=sysRoleRepo.findByUuid(roleid);

        //查询全部的权限
        List<SysAuth> sysAuths=findAll();

        //以全部权限为基准进行循环，并和角色对应的权限进行比较，用来设定权限的open和checked属性
        for(SysAuth sa:sysAuths)
        {
            //遍历角色对应的权限，比较是否在全部权限之中（在其中时，则勾选属性为true）
            boolean blChecked=false;
            if(!roleid.equals("nouuid"))
            {
                String strRoleAuthName=sa.getFullname();
                for(SysAuth roleAuth:sysRole.getSysAuths())
                {
                    if(roleAuth.getFullname().equals(strRoleAuthName))
                    {
                        blChecked=true;
                        break;
                    }
                }
            }

            Ztree z=new Ztree();
            z.id=sa.getId();
            z.pId=sa.getPid();
            z.name=sa.getTreename();
            z.open=true;
            z.checked=blChecked;
            listztree.add(z);
        }
        return listztree;
    }

    @Override
    public String saveChildAuth(int id, String childname)
    {
        if(id==0)//一级权限
        {
            SysAuth sysAuth_Child=sysAuthRepo.findByFullname(childname);
            if(sysAuth_Child!=null)//子节点已经存在
            {
                JSONObject result = new JSONObject();
                result.put("msg","exist");
                return result.toJSONString();
            }
            else//节点未存在
            {
                SysAuth newAuth=new SysAuth();
                newAuth.setFullname(childname);//一级节点的全称和树型显示名称一致
                newAuth.setPid(id);
                newAuth.setId(findMaxId(id));
                newAuth.setTreename(childname);//一级节点的全称和树型显示名称一致
                sysAuthRepo.save(newAuth);

                JSONObject result = new JSONObject();
                result.put("msg","ok");
                return result.toJSONString();
            }
        }
        else//非一级权限
        {
            SysAuth sysAuth_Parent=sysAuthRepo.findById(id);
            String strChildName=sysAuth_Parent.getFullname()+"_"+childname;
            SysAuth sysAuth_Child=sysAuthRepo.findByFullname(strChildName);
            if(sysAuth_Child!=null)//子节点已经存在
            {
                JSONObject result = new JSONObject();
                result.put("msg","exist");
                return result.toJSONString();
            }
            else//子节点未存在
            {
                SysAuth newAuth=new SysAuth();
                newAuth.setFullname(strChildName);
                newAuth.setPid(id);
                newAuth.setId(findMaxId(id));
                newAuth.setTreename(childname);
                sysAuthRepo.save(newAuth);

                JSONObject result = new JSONObject();
                result.put("msg","ok");
                return result.toJSONString();
            }
        }

    }

    @Override
    public SysAuth findById(int id)
    {
        return sysAuthRepo.findById(id);
    }

    @Override
    public SysAuth findByFullname(String fullname)
    {
        return sysAuthRepo.findByFullname(fullname);
    }

    @Override
    public int findMaxId(int pid)
    {
        //判断是否有子节点
        List<SysAuth> sysAuths=sysAuthRepo.findAllChildByPid(pid);
        if(sysAuths.size()==0)//没有子节点
        {
            int intNewId=pid*10+1;
            return intNewId;
        }
        else//有子节点
        {
            return sysAuthRepo.findMaxId(pid)+1;
        }
    }

    @Override
    @Transactional
    public String deleteByChild(int id)
    {
        SysAuth sysAuth=sysAuthRepo.findById(id);
        //先批量删除角色权限中间表对应的记录
        List<SysAuth> list=sysAuthRepo.findAllByFullname(sysAuth.getFullname()+"%");
        for (SysAuth sa:list)
        {
            sysAuthRepo.deleteMaptabByUuid(sa.getUuid());
        }

        //批量删除本身节点及子节点
        sysAuthRepo.deleteByName(sysAuth.getFullname()+"%");
        return JSON.toJSONString(sysAuth);//这里有返回值，主要是为后面的基于AOP的日志捕捉服务的
    }

    @Override
    @Transactional
    public void deleteByName(String name)
    {
        sysAuthRepo.deleteByName(name+"%");
    }

    @Override
    public void editRole(String uuid, String authsinfo)
    {
        /**
         * 处理前端传过来的字符串形式组装的多权限信息，建立角色和权限的映射关系
         */
        //得到角色信息
        SysRole sysRole=sysRoleRepo.findByUuid(uuid);

        //根据勾选的权限节点id，得到对应的权限对象，并给角色中的权限集合属性赋值
        List<SysAuth> list=new ArrayList<SysAuth>();
        String[] arrAuthid=authsinfo.split("\\$");
        for(int i=0,num=arrAuthid.length;i<num;i++)
        {
            SysAuth sysAuth=sysAuthRepo.findById(Integer.parseInt(arrAuthid[i]));
            list.add(sysAuth);
        }
        sysRole.setSysAuths(list);

        //保存或者更新角色信息
        sysRoleRepo.save(sysRole);
    }
}
