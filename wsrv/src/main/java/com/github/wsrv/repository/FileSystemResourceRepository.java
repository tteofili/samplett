package com.github.wsrv.repository;


import com.github.wsrv.Resource;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tommaso
 */
public class FileSystemResourceRepository implements ResourceRepository {
  private String baseDir;
  private ExecutorService executorService;

  @Override
  public void initialize(String root) {
    this.baseDir = root;
    this.executorService = Executors.newCachedThreadPool();
  }

  @Override
  public Resource getResource(String resourceName) throws ResourceNotFoundException, NotReadableResourceException {
    try {
      return executorService.submit(new FSRequestHandlerThread(new StringBuilder(baseDir).append(resourceName).
              toString())).get();
    } catch (InterruptedException e) {
      throw new ResourceNotFoundException(e);
    } catch (ExecutionException e) {
      throw new ResourceNotFoundException(e);
    }
  }
}
