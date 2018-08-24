package com.codemonkey.bag;

import lombok.Data;

@Data
public class BagItem {// 定义一个物品
	private String id; // 物品id
	private int size = 0;// 物品所占空间
	private double value = 0;// 物品价值

    public BagItem(String id, int size, double value) {
        this.id = id;
        this.size = size;
        this.value = value;
    }
}
