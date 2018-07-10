package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ThreadUtils<T> {

	private ExecutorService exec;
	private CompletionService<T> completionService;
	private int poolSize = 10;
	
	public ThreadUtils(int poolSize){
		this.poolSize = poolSize;
	}
	
	
	public List<T> runWithCompletionService(List<Callable<T>> list){
		
		if(SysUtils.isEmpty(list)){
			return null;
		}
		
		List<T> result = new ArrayList<T>();
		exec = Executors.newFixedThreadPool(poolSize);
		completionService = new ExecutorCompletionService<T>(exec);
		
		for(Callable<T> c : list) {
			if (!exec.isShutdown()) {
				completionService.submit(c);
			}
		}
		for (int i = 0; i < poolSize; i++) {			
			try {
				result.add(completionService.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<T> runWithFutureTask(List<Callable<T>> list){
		
		List<Future<T>> tasks = new ArrayList<Future<T>>();
		 
		if(SysUtils.isEmpty(list)){
			return null;
		}
		
		List<T> result = new ArrayList<T>();
		exec = Executors.newFixedThreadPool(poolSize);
		
		for(Callable<T> c : list) {
			if (!exec.isShutdown()) {
			}
			FutureTask<T> t = new FutureTask<T>(c);
			tasks.add(t);
			if (!exec.isShutdown()) {
				exec.submit(t);
			}
		}
		
		for (Future<T> task : tasks) {
			try {
				// 如果计算未完成则阻塞
				result.add(task.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
