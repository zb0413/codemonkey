package com.codemonkey.designpattern.proxy;

/**
 * 真实实体类
 * 
 * 
 *
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
	System.out.println("真实对象的请求");
    }

}
