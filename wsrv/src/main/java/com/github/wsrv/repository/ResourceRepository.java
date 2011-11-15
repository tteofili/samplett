package com.github.wsrv.repository;

import com.github.wsrv.Resource;

/**
 * a {@link ResourceRepository} is responsible for the actual fetch of a {@link Resource}, depending on the type
 * of backed repository
 *
 * @author tommaso
 */
public interface ResourceRepository {
  void initialize(String root);

  Resource getResource(String resourceName) throws ResourceNotFoundException, NotReadableResourceException;
}
