package com.codemonkey.test.drools;
/*
喝汽水问题：
1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：你有50元钱，最多可以喝到几瓶汽水？
*/
public class BottleProblem{
	public int totalMoney = 50;
	public int totalDrinked = 0;
	public int exchangedQty = 2;
	public int onHandBottles = 0;
	
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getTotalDrinked() {
		return totalDrinked;
	}
	public void setTotalDrinked(int totalDrinked) {
		this.totalDrinked = totalDrinked;
	}
	public int getExchangedQty() {
		return exchangedQty;
	}
	public void setExchangedQty(int exchangedQty) {
		this.exchangedQty = exchangedQty;
	}
	public int getOnHandBottles() {
		return onHandBottles;
	}
	public void setOnHandBottles(int onHandBottles) {
		this.onHandBottles = onHandBottles;
	}
}
