package com.github.wsrv.nio;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCacheProvider;
import com.github.wsrv.jetty.repository.FSRequestHandlerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author tommaso
 */
public class ReadSocketHandler implements Callable<Object> {

  private final Logger log = LoggerFactory.getLogger(ReadSocketHandler.class);

  private Socket socket;

  public ReadSocketHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public Object call() throws Exception {
    try {
      log.info(new StringBuilder("reading from socket").append(socket.toString()).toString());
      // allocate the buffer
      ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
      // read the request into the buffer
      socket.getChannel().read(byteBuffer);
      // make the buffer available for read
      byteBuffer.flip();
      // read the buffer
      byte[] requestInByte = new byte[byteBuffer.limit()];
      for (int i = 0; byteBuffer.hasRemaining(); i++) {
        requestInByte[i] = byteBuffer.get();
      }
      String requestString = new String(requestInByte);
      HttpRequestParser httpRequestParser = new HttpRequestParser();
      HttpRequest httpRequest = httpRequestParser.parse(requestString);
      if (requestString.length() > 20 && requestString.startsWith("GET")) {
//        log.info("parsed HTTP request :\n{}", httpRequest);
        Future<Resource> fut = Executors.newCachedThreadPool().submit(new FSRequestHandlerThread("." + httpRequest.getPath()));
        Resource r = fut.get();
        ResourceCacheProvider.getInstance().getCache("in-memory").put("11", r);
//        log.info(new StringBuilder("retrieved resource ").append(r.getBytes()).toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
//      socket.close();
    }
    return null;
  }
}
