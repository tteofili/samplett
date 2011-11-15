package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class URLBasedResourceRepositoryTest {
  @Test
  public void testResourceFetch() {
    try {
      URLBasedResourceRepository resourceRepository = new URLBasedResourceRepository();
      resourceRepository.initialize("http://people.apache.org");
      Resource resource = resourceRepository.getResource("/resources");
      assertNotNull(resource);
      assertNotNull(resource.getBytes());
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
