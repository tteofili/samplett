package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import org.testng.annotations.Test;

import java.net.URL;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class FileSystemResourceRepositoryTest {
  @Test
  public void testResourceFetch() {
    try {
      FileSystemResourceRepository fileSystemResourceRepository = new FileSystemResourceRepository();
      fileSystemResourceRepository.initialize("");
      URL resourceURL = getClass().getResource("/wiki.html");
      Resource resource = fileSystemResourceRepository.getResource(resourceURL.getFile());
      assertNotNull(resource);
      assertNotNull(resource.getBytes());
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
