package com.github.wsrv.jetty.repository;

import com.github.wsrv.Resource;

/**
 * @author tommaso
 */
public interface ResourceRepository {
  void initialize(String root);

  Resource getResource(String resourceName) throws ResourceNotFoundException;
}
