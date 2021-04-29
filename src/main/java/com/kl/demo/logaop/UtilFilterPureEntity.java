package com.kl.demo.logaop;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤实体中的非基本属性（如list,mangtomany等），得到只含基本属性的map对象
 */
public class UtilFilterPureEntity
{
    static String[] types = {"class java.lang.Integer", "class java.lang.Double",
            "class java.lang.Float", "class java.lang.Long", "class java.lang.Short",
            "class java.lang.Byte", "class java.lang.Boolean", "class java.lang.Char",
            "class java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    /**
     * 根据类名称，得到类的属性及属性值
     */
    public static Map<String, Object> getKeyAndValue(Object obj)
    {
        Map<String, Object> map = new HashMap<>();
        Class userCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = userCla.getDeclaredFields();//得到类中的所有属性集合
        for (int i = 0; i < fs.length; i++)
        {
            Field f = fs[i];
            f.setAccessible(true); // 设置属性是可以访问的

            //判断属性是否是基础类型（防止配置的映射关系造成的大数据量,如list，manytomany）
            String typeName=f.getGenericType().toString();
            boolean blBasicType=false;//属性是否是基础类型
            for (String t : types)
            {
                if(t.equals(typeName))
                {
                    blBasicType=true;
                    break;
                }
            }
            if(!blBasicType)//不是基础类型，则略过该属性
            {
                continue;
            }

            Object val = new Object();
            try
            {
                val = f.get(obj);// 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}

