package com.kl.demo.controller;

public class TestEntity {
    public String url;
    public int count;
    public double height;

    public TestEntity() {}

    public TestEntity(String url, int count, double height) {
        this.url = url;
        this.count = count;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
