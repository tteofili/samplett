package com.github.wsrv.nio;

import com.github.wsrv.Resource;
import com.github.wsrv.jetty.repository.FSRequestHandlerThread;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class SocketHandler implements Callable<Object> {
  private final Logger log = LoggerFactory.getLogger(SocketHandler.class);

  private Socket socket;

  public SocketHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public Object call() throws Exception {
    try {
      // read from the socket inpustream
      InputStream socketInputStream = socket.getInputStream();
      byte[] b = new byte[2048];
      socketInputStream.read(b);
      String requestString = new String(b);
      if (requestString.length() > 20 && requestString.startsWith("GET")) {
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        HttpRequest httpRequest = httpRequestParser.parse(requestString);
//      if (requestString.length() > 20 && requestString.startsWith("GET")) {
        log.info("parsed HTTP request :\n{}", httpRequest);
        // check the cache
        Resource resource = new FSRequestHandlerThread("." + httpRequest.getPath()).call();
        // write response
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatusCode(200);
        httpResponse.setVersion(httpRequest.getVersion());
        httpResponse.addHeader("ETag", String.valueOf(httpResponse.hashCode()));
        httpResponse.addHeader("Content-Length", String.valueOf(resource.getBytes().length));
        httpResponse.setResource(resource);
        log.info("parsed HTTP response :\n{}", httpResponse);
        IOUtils.write(httpResponse.toString(), socket.getOutputStream());
      }
    } catch (Exception e) {
      throw e;
    } finally {
      socket.close();
    }
    return null;
  }
}
