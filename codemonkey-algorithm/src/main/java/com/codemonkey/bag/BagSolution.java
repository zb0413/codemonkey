package com.codemonkey.bag;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BagSolution { // 定义一个打包方式
    
	private List<BagItem> items = new ArrayList<BagItem>();// 包里的物品集合

    BagSolution() {
    }

    public double getValue() {// 包中物品的总价值
        double value = 0;
        for (BagItem item : items) {
            value += item.getValue();
        }
        return value;
    }

    public int getSize() {// 包中物品的总大小
        int size = 0;
        for (BagItem item : items) {
            size += item.getSize();
        }
        return size;
    }
}
