package com.github.wsrv;


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
    File file = new File(pathName);
    final byte[] byteStream;
    try {
      byteStream = IOUtils.toByteArray(new FileInputStream(file));
    } catch (Exception e) {
      throw new ResourceNotFoundException(e);
    }
    return new WSRVResource() {
      @Override
      public byte[] getBytes() {
        return byteStream;
      }
    };
  }
}
