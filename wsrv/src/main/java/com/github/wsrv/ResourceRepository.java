package com.github.wsrv;

/**
 * @author tommaso
 */
public interface ResourceRepository {
  void initialize(String root);

  WSRVResource getResource(String resourceName) throws ResourceNotFoundException;
}
