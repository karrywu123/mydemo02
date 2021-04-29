package com.kl.demo.domain;

/**
 * 用于echarts的实体，与数据库表无关
 */
public class EchartsData
{
    private String name;
    private float value;

    public EchartsData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}