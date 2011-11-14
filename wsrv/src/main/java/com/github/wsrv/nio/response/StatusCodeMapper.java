package com.github.wsrv.nio.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class StatusCodeMapper {

  private static final Map<Integer, String> codeStatusMap = new HashMap<Integer, String>() {{
    put(200, "OK");
    put(404, "NOT FOUND");
    put(403, "FORBIDDEN");
    put(503, "INTERNAL SERVER ERROR");
  }};

  public static String map(Integer statusCode) {
    return codeStatusMap.get(statusCode);
  }
}
