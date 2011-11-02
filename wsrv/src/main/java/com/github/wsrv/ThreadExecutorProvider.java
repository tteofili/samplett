package com.github.wsrv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tommaso
 */
public class ThreadExecutorProvider {
  private static ThreadExecutorProvider instance = new ThreadExecutorProvider();

  private final ExecutorService executor;

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

  public ExecutorService getExecutor() {
    return executor;
  }
}
