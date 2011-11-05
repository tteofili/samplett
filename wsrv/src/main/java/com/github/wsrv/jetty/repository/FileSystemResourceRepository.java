package com.github.wsrv.jetty.repository;


import com.github.wsrv.jetty.WSRVResource;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author tommaso
 */
public class FileSystemResourceRepository implements ResourceRepository {
  private String baseDir;

  @Override
  public void initialize(String root) {
    this.baseDir = root;
  }

  @Override
  public WSRVResource getResource(String resourceName) throws ResourceNotFoundException {
    String pathName = new StringBuilder(baseDir).append(resourceName).toString();
    final byte[] byteStream;
    File file = new File(pathName);
    if (!file.exists()) {
      throw new ResourceNotFoundException(new StringBuilder(pathName).append(" not found").toString());
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
    return new WSRVResource() {
      @Override
      public byte[] getBytes() {
        return byteStream;
      }
    };
  }
}
