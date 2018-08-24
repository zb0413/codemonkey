package com.codemonkey.designpattern.adapter;

/**
 * 客户所期待的接口
 * 
 * 
 *
 */
public abstract class Target {
    public void request() {
	System.out.println("普通请求！");
    }
}
