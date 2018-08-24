package com.codemonkey.designpattern.factory.method;

import com.codemonkey.designpattern.factory.simple.Operation;

/**
 * 工厂方法客户端
 * 
 * 
 *
 */
public class FactoryClient {
    public static void main(String[] args) {
	IFactory operFactory = new DivFactory();
	Operation operation = operFactory.createOperation();

	operation.numberA = 3.4;
	operation.numberB = 4.5;

	System.out.println(operation.result());

    }
}