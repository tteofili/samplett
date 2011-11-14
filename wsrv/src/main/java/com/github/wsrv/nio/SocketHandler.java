package com.github.wsrv.nio;

import com.github.wsrv.Resource;
import com.github.wsrv.jetty.repository.FSRequestHandlerThread;
import com.github.wsrv.nio.request.HttpRequest;
import com.github.wsrv.nio.request.HttpRequestParser;
import com.github.wsrv.nio.response.HttpResponse;
import com.github.wsrv.nio.response.HttpResponseFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class SocketHandler implements Callable<Long> {
  private final Logger log = LoggerFactory.getLogger(SocketHandler.class);

  private Socket socket;

  public SocketHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public Long call() throws Exception {
    try {
      long s = System.nanoTime();
      run();
      long e = System.nanoTime();
      return e - s;
    } catch (Exception e) {
      throw e;
    }
  }

  public void run() {
    try {
      // read from the socket inpustream
      InputStream socketInputStream = socket.getInputStream();
      byte[] b = new byte[2048];
      socketInputStream.read(b);
      String requestString = new String(b);
      if (requestString.length() > 20) {
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        HttpRequest httpRequest = httpRequestParser.parse(requestString);
        log.info("parsed HTTP request :\n{}", httpRequest);
        // check the cache
        Resource resource = new FSRequestHandlerThread("." + httpRequest.getPath()).call();
        // write response
        HttpResponse httpResponse = HttpResponseFactory.createResponse(httpRequest, resource);
        log.info("parsed HTTP response :\n{}", httpResponse);
        IOUtils.write(httpResponse.toString(), socket.getOutputStream());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        // do nothing
      }
    }
  }
}
