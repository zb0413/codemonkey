package com.codemonkey.designpattern.factory.method;

import com.codemonkey.designpattern.factory.simple.Operation;
import com.codemonkey.designpattern.factory.simple.OperationAdd;
import com.codemonkey.designpattern.factory.simple.OperationDiv;
import com.codemonkey.designpattern.factory.simple.OperationMul;
import com.codemonkey.designpattern.factory.simple.OperationSub;

/**
 * 工厂接口
 * 
 * 
 *
 */
public interface IFactory {
    public Operation createOperation();
}

class AddFactory implements IFactory {

    @Override
    public Operation createOperation() {
	return new OperationAdd();
    }

}

class SubFactory implements IFactory {

    @Override
    public Operation createOperation() {
	return new OperationSub();
    }

}

class MulFactory implements IFactory {

    @Override
    public Operation createOperation() {
	return new OperationMul();
    }

}

class DivFactory implements IFactory {

    @Override
    public Operation createOperation() {
	return new OperationDiv();
    }

}