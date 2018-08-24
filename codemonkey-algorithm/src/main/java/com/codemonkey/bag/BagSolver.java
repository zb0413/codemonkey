package com.codemonkey.bag;

import lombok.Data;

@Data
public class BagSolver {

	private BagItem[] sourceItems;
	private int bagSize;
	private int itemCount;
	// 保存各种情况下的最优打包方式 第一维度为物品数量从0到itemCount,第二维度为包裹大小从0到bagSize
	private BagSolution[][] okBags;
	
	public BagSolver(BagItem[] sourceItems , int bagSize){
		this.sourceItems = sourceItems;
		this.bagSize = bagSize;
		this.itemCount = sourceItems.length;
		this.okBags = new BagSolution[itemCount + 1][bagSize + 1];
	}

    void init() {
        for (int i = 0; i < bagSize + 1; i++) {
            okBags[0][i] = new BagSolution();
        }

        for (int i = 0; i < itemCount + 1; i++) {
            okBags[i][0] = new BagSolution();
        }
    }

    public void solve() {
        init();
        for (int iItem = 0 ; iItem < itemCount ; iItem++) {
            for (int curBagSize = 0 ; curBagSize < bagSize ; curBagSize++) {
                okBags[iItem][curBagSize] = new BagSolution();
                if (sourceItems[iItem].getSize() > curBagSize) {// 当前物品大于包空间.肯定不能放入包中.
                    okBags[iItem][curBagSize].getItems().addAll(okBags[iItem][curBagSize].getItems());
                } else {
                    double notIncludeValue = okBags[iItem][curBagSize].getValue();// 不放当前物品包的价值
                    int freeSize = curBagSize - sourceItems[iItem].getSize();// 放当前物品包剩余空间
                    double includeValue = sourceItems[iItem - 1].getValue() + okBags[iItem - 1][freeSize].getValue();// 当前物品价值+放了当前物品后剩余包空间能放物品的价值
                    if (notIncludeValue < includeValue) {// 放了价值更大就放入.
                        okBags[iItem][curBagSize].getItems().addAll(okBags[iItem][freeSize].getItems());
                        okBags[iItem][curBagSize].getItems().add(sourceItems[iItem]);
                    } else {// 否则不放入当前物品
                        okBags[iItem][curBagSize].getItems().addAll(okBags[iItem][curBagSize].getItems());
                    }
                }
            }
        }
    }
}
