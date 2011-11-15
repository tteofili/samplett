package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.configuration.ServerConfiguration;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class FSRequestHandlerThread implements Callable<Resource> {
  private final String resourcePath;

  public FSRequestHandlerThread(String resourcePath) {
    this.resourcePath = new StringBuilder(ServerConfiguration.getInstance().getRoot()).append(resourcePath).toString();
  }

  public Resource call() throws Exception {
    final byte[] byteStream;
    File file = new File(resourcePath);
    if (!file.exists()) {
      throw new ResourceNotFoundException(new StringBuilder(resourcePath).append(" not found").toString());
    }
    if (!file.canRead()) {
      throw new NotReadableResourceException(new StringBuffer(resourcePath).append(" cannot be read").toString());
    }
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
    return new Resource() {
      @Override
      public byte[] getBytes() {
        return byteStream;
      }
    };
  }
}
