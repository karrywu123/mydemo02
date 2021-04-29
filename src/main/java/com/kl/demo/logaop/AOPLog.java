package com.kl.demo.logaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AOPLog
{
    /**
     * 主要包括的类型：查询、新增、修改、删除、其它；
     * 建议就用以上类型，但写其它类型也可以，没有限制
     * 字数在20字以内
     */
    String operatetype() default " ";

    /**
     * 简洁的对操作进行描述，字数在100以内
     */
    String operatedesc() default " ";
}
