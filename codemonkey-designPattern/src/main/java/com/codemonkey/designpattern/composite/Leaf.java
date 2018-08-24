package com.codemonkey.designpattern.composite;


/**
 * Leaf在组合中表示叶节点对象，叶节点没有子节点
 * 
 * 
 *
 */
public class Leaf extends Component {

    public Leaf(String name) {
	super(name);
    }

    @Override
    public void add(Component component) {
	System.out.println("cannot add to a leaf");
    }

    @Override
    public void remove(Component component) {
	System.out.println("cannot remove from a leaf");
    }

    @Override
    public void display(int depth) {
	// 通过“-”的数目显示级别
	System.out.println(depth + '-' + this.name);
    }

}
