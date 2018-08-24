package com.codemonkey.designpattern.factory.simple;

/**
 * 减法类
 * 
 * 
 *
 */
public class OperationSub extends Operation {

    @Override
    public double result() {
	return numberA - numberB;
    }

}