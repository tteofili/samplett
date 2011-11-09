package com.github.wsrv.nio;

import com.github.wsrv.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class SocketChannelHandler implements Callable<Object> {
  private final Logger log = LoggerFactory.getLogger(SocketChannelHandler.class);

  private SocketChannel socket;

  public SocketChannelHandler(SocketChannel socket) {
    this.socket = socket;
  }

  @Override
  public Object call() throws Exception {
    try {
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      socket.read(byteBuffer);
      byteBuffer.flip();
      // parse the http request
      String requestString = new String(byteBuffer.array());
      if (requestString.length() > 20 && requestString.startsWith("GET")) {
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        HttpRequest httpRequest = httpRequestParser.parse(requestString);
        byteBuffer.clear(); //make buffer ready for writing
        log.info("parsed HTTP request :\n{}", httpRequest);
        Resource resource = null;
//    log.info("parsed request :\n " + httpRequest.toString());
        // check the cache
//    ResourceCache<HttpRequest, Resource> cache = ResourceCacheProvider.getInstance().getCache("in-memory");
//    Resource resource = cache.get(httpRequest);
//    if (resource == null) {
//      // retrieve the resource
//      final byte[] byteStream;
//      String path = httpRequest.getPath();
//      File file = new File(path);
//      if (!file.exists()) {
//        throw new FileNotFoundException(new StringBuilder(path).append(" not found").toString());
//      }
//      if (file.isFile()) {
//        try {
//          byteStream = IOUtils.toByteArray(new FileInputStream(file));
//        } catch (Exception e) {
//          throw new FileNotFoundException(e.getLocalizedMessage());
//        }
//      } else {
//        StringBuilder sb = new StringBuilder();
//        for (File f : file.listFiles()) {
//          sb.append(f.getName()).append("\n");
//        }
//        byteStream = sb.toString().getBytes();
//      }
//      resource = new Resource() {
//        @Override
//        public byte[] getBytes() {
//          return byteStream;
//        }
//      };
//      cache.put(httpRequest, resource);
//    }

        // write response
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatusCode(200);
        httpResponse.setVersion(httpRequest.getVersion());
        httpResponse.addHeader("ETag", "111");
        httpResponse.setResource(resource);
        log.info("parsed HTTP response :\n  {}", httpResponse);
        byteBuffer.put(httpResponse.toString().getBytes());
        socket.write(byteBuffer);
      }
    } catch (Exception e) {
      e.printStackTrace();
      socket.close();
      throw e;
    }
    return null;
  }
}
