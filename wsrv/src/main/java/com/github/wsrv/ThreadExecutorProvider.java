package com.github.wsrv;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author tommaso
 */
public class ThreadExecutorProvider {
  private static ThreadExecutorProvider instance = new ThreadExecutorProvider();

  private Executor executor;

  public static ThreadExecutorProvider getInstance() {
    return instance;
  }

  public static void initialize(Integer poolSize) {
    instance = new ThreadExecutorProvider(poolSize);
  }

  private ThreadExecutorProvider() {
    executor = Executors.newCachedThreadPool();
  }

  private ThreadExecutorProvider(Integer poolSize) {
    executor = Executors.newFixedThreadPool(poolSize);
  }

  public Executor getExecutor() {
    return executor;
  }
}
