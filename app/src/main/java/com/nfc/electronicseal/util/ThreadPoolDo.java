package com.nfc.electronicseal.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: caoli
 * @CreateDate: 2016-7-13
 * @Description: 线程池
 */
public class ThreadPoolDo {
	private static ThreadPoolDo mThreadPoolDo;
	private ThreadPoolExecutor threadPool = null;
	
	private ThreadPoolDo(){
		threadPool = new ThreadPoolExecutor(4, 10, 3, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public static ThreadPoolDo getInstance(){
		if(mThreadPoolDo==null)
			mThreadPoolDo = new ThreadPoolDo();
		return mThreadPoolDo;
	}
	
	public void executeThread(Thread thread){
		threadPool.execute(thread);
	}
}
