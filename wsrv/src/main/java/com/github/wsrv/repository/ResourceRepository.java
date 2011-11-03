package com.github.wsrv.repository;

import com.github.wsrv.WSRVResource;

/**
 * @author tommaso
 */
public interface ResourceRepository {
  void initialize(String root);

  WSRVResource getResource(String resourceName) throws ResourceNotFoundException;
}
