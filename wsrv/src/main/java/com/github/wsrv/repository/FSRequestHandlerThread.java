package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

/**
 * Thread that actually gets the {@link File} bytes from the file system or a String representing the list of files
 * contained in a directory, if the {@link Resource} is a directory
 *
 * @author tommaso
 */
public class FSRequestHandlerThread implements Callable<Resource> {
  private final Logger log = LoggerFactory.getLogger(FSRequestHandlerThread.class);

  private final String resourcePath;

  public FSRequestHandlerThread(String resourcePath) {
    this.resourcePath = resourcePath;
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
    if (log.isInfoEnabled())
      log.info(new StringBuilder(resourcePath).append(" retrieved succesfully").toString());
    return new Resource() {
      @Override
      public byte[] getBytes() {
        return byteStream;
      }
    };
  }
}
