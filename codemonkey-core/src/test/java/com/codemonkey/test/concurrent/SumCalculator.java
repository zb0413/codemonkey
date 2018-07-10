package com.codemonkey.test.concurrent;

import java.util.List;
import java.util.concurrent.Callable;

class SumCalculator implements Callable<Long> {
	private List<Integer> numbers;
	private int start;
	private int end;

	public SumCalculator(final List<Integer> numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	public Long call() throws Exception {
		Long sum = 0l;
		for (int i = start; i < end; i++) {
			sum += numbers.get(i);
		}
		return sum;
	}
}
