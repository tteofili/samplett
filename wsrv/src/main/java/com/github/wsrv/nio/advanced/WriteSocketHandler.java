package com.github.wsrv.nio.advanced;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCache;
import com.github.wsrv.cache.ResourceCacheProvider;
import com.github.wsrv.nio.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
class WriteSocketHandler implements Callable<Object> {
  private final Logger log = LoggerFactory.getLogger(WriteSocketHandler.class);

  private final Socket socket;

  public WriteSocketHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public Object call() throws Exception {
    try {
      if (socket.getChannel().isConnected()) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        ResourceCache<String, Resource> cache = ResourceCacheProvider.getInstance().getCache("in-memory");
        Resource r = cache.get("11");
        if (r != null) {
          // write response
          HttpResponse httpResponse = new HttpResponse();
          httpResponse.setStatusCode(200);
          httpResponse.setStatusMessage("OK");
          httpResponse.setVersion("HTTP/1.1");
          httpResponse.addHeader("Server", "wsrv");
          httpResponse.addHeader("Date", new Date().toString());
          httpResponse.addHeader("ETag", String.valueOf(httpResponse.hashCode()));
          httpResponse.addHeader("Content-Type", "text/plain");
          httpResponse.addHeader("Content-Length", String.valueOf(r.getBytes().length));
          httpResponse.setResource(r);
//          log.info("parsed HTTP response :\n{}", httpResponse);
          byteBuffer.put(httpResponse.toString().getBytes());
          socket.getChannel().write(byteBuffer);
        }
        byteBuffer.clear();
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (!socket.isClosed())
        socket.close();
    }
    return null;
  }
}
