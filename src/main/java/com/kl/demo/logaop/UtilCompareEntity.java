package com.kl.demo.logaop;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 比较两个实体的value值的差异，输出不一致的属性，主要用于日志中记录实体各项属性修改前和修改后的值
 */
public class UtilCompareEntity
{
    private  String jsoninfo;//修改详情
    private  Object oldEntity;//修改前实体
    private Object newEntity;//修改后实体

    public UtilCompareEntity(Object oldEntity, Object newEntity) {
        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }

    public String outputInfo()
    {
        Map<String,Object> mapBefore = UtilFilterPureEntity.getKeyAndValue(oldEntity);//修改前的实体信息
        Map<String,Object> mapAfter = UtilFilterPureEntity.getKeyAndValue(newEntity);//修改后的实体信息
        Map<String,String> mapNew=new HashMap<String,String>();
        for (String key:mapBefore.keySet()) {
            String strBefore="";
            if(mapBefore.get(key)!=null){strBefore=mapBefore.get(key).toString();}
            String strAfter="";
            if(mapAfter.get(key)!=null){strAfter=mapAfter.get(key).toString();}
            if(!strBefore.equals(strAfter))
            {
                System.out.println(key);
                if(!(key.equals("password")||key.equals("roleuuid")))
                {
                    mapNew.put(key,strBefore+" => "+strAfter);
                }
            }
        }
        return JSON.toJSONString(mapNew);
    }
}
