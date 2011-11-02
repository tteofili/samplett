package com.github.wsrv;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
class FSRequestHandlerThread implements Callable<WSRVResource> {
  private final String resourcePath;

  public FSRequestHandlerThread(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public WSRVResource call() throws Exception {
    final byte[] byteStream;
    File file = new File(resourcePath);
    if (file.isFile()) {
      try {
        byteStream = IOUtils.toByteArray(new FileInputStream(file));
      } catch (Exception e) {
        throw new ResourceNotFoundException(e);
      }
    } else {
      StringBuilder sb = new StringBuilder();
      for (File f : file.listFiles()) {
        sb.append(f.getName()).append("\n");
      }
      byteStream = sb.toString().getBytes();
    }
    return new WSRVResource() {
      @Override
      public byte[] getBytes() {
        return byteStream;
      }
    };
  }
}
