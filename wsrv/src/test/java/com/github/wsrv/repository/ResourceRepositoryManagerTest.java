package com.github.wsrv.repository;

import com.github.wsrv.nio.configuration.ServerConfiguration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class ResourceRepositoryManagerTest {
  @Test
  public void testDefaultResourceRepositoryWithoutExplicitServerInitialization() {
    try {
      ResourceRepositoryManager resourceRepositoryManager = ResourceRepositoryManager.getInstance();
      ResourceRepository resourceRepository = resourceRepositoryManager.getResourceRepository();
      assertTrue(resourceRepository instanceof FileSystemResourceRepository);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testResourceRepositoryFetchWithWrongExplicitServerInitialization() {
    try {
      ServerConfiguration.initialize(20, "unexistent", ".");
      ResourceRepositoryManager.getInstance();
      fail("unexistent repo type is not existing thus it shouldn't be possible to get the ResourceRepository");
    } catch (Throwable e) {
      // everything ok
    }
  }
}
