package com.codemonkey.designpattern.factory.simple;

/**
 * 乘法类
 * 
 * 
 *
 */
public class OperationMul extends Operation {

    @Override
    public double result() {
	return numberA * numberB;
    }

}