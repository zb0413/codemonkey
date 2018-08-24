package com.codemonkey.designpattern.memento;

/**
 * 发起人（Originator） 类
 * 
 * 
 *
 */
public class Originator {

    private String state;

    public Memento createMemento() {
	return new Memento(this.state);
    }

    public void recoverMemento(Memento memento) {
	this.setState(memento.getState());
    }

    public void show() {
	System.out.println("state = " + this.state);
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

}
