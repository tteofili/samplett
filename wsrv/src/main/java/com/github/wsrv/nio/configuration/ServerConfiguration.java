package com.github.wsrv.nio.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Singleton class which holds the server configuration
 *
 * @author tommaso
 */
public class ServerConfiguration {

  private static ServerConfiguration instance;
  private static final String DEFAULT_ROOT = ".";
  private static final int DEFAULT_POOL_SIZE = 10;

  private final String root;
  private final Integer poolSize;
  private final List<String> supportedMethods;

  public static ServerConfiguration getInstance() {
    if (instance == null) {
      instance = new ServerConfiguration(); // return default configuration if not explicitly initialized
    }
    return instance;
  }

  public static void initialize(Integer poolSize, String root, List<String> supportedMethods) {
    if (instance != null)
      throw new RuntimeException("ServerConfiguration has already been initialized");
    instance = new ServerConfiguration(poolSize, root, supportedMethods);
  }

  private ServerConfiguration() {
    this(DEFAULT_POOL_SIZE, DEFAULT_ROOT); // default parameters
  }

  private ServerConfiguration(Integer poolSize, String root) {
    this(poolSize, root, Arrays.asList("GET"));
  }

  private ServerConfiguration(Integer poolSize, String root, List<String> supportedMethods) {
    this.poolSize = poolSize;
    this.root = root;
    this.supportedMethods = Collections.unmodifiableList(supportedMethods);
  }

  public String getRoot() {
    return root;
  }

  public Integer getPoolSize() {
    return poolSize;
  }

  public List<String> getSupportedMethods() {
    return supportedMethods;
  }
}
