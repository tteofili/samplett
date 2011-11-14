package com.github.wsrv.repository;

import com.github.wsrv.Resource;

/**
 * @author tommaso
 */
public interface ResourceRepository {
  void initialize(String root);

  Resource getResource(String resourceName) throws ResourceNotFoundException;
}
