package com.kl.demo.service;

import com.kl.demo.domain.SysAuth;
import com.kl.demo.domain.Ztree;
import java.util.List;

/**
 * 权限业务层接口
 */
public interface SysAuthService {
    /**
     * //查找全部权限
     * @return List<SysAuth>
     */
    List<SysAuth> findAll();

    /**
     * 查找全部权限（为zTree准备的集合）,并且和角色对应的权限进行比较，以便判断是否展开和勾选，默认传入的roleid为‘nouuid’
     * @return List<Ztree>
     */
    List<Ztree> findALLToZtree(String roleid);

    /**
     * 添加节点的子节点；1）首先根据节点id查询到对应的节点信息；2）再根据该节点信息和新的节点名称进行名称组合，以该组合名称查询对应的节点信息是否存在；3）如果新节点信息不存在，则保存该新节点
     * id是当前选定节点的id，name是需要新增加子节点的名称
     */
    String saveChildAuth(int id,String childname);

    /**
     * 根据id查找对应的权限信息
     */
    SysAuth findById(int id);

    /**
     * 根据节点的全称，查找对应的权限信息
     */
    SysAuth findByFullname(String fullname);

    /**
     * 根据父节点的id，获取其子节点的最大id
     */
    int findMaxId(int pid);

    /**
     * 根据节点的id删除节点及子节点
     */
    String deleteByChild(int id);

    /**
     * 根据name删除指定的节点及子节点
     */
    void deleteByName(String name);

    /**
     * 给选定的角色赋予权限,其中‘authsinfo’是以$分割的节点id字符串
     */
    void editRole(String uuid,String authsinfo);
}
