package com.github.wsrv;

import com.github.wsrv.jetty.repository.FileSystemResourceRepository;
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
      WSRVResource wsrvResource = fileSystemResourceRepository.getResource(resourceURL.getFile());
      assertNotNull(wsrvResource);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
