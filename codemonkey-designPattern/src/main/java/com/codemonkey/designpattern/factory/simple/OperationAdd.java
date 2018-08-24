package com.codemonkey.designpattern.factory.simple;

/**
 * 加法类
 * 
 * 
 *
 */
public class OperationAdd extends Operation {

    @Override
    public double result() {
	return numberA + numberB;
    }

}