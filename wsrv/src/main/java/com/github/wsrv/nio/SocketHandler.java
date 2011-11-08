package com.github.wsrv.nio;

import com.github.wsrv.cache.WSRVResourceCache;
import com.github.wsrv.cache.WSRVResourceCacheProvider;
import com.github.wsrv.jetty.WSRVResource;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class SocketHandler implements Callable<Object> {
  private SocketChannel socket;

  public SocketHandler(SocketChannel socket) {
    this.socket = socket;
  }

  @Override
  public Object call() throws Exception {
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    socket.read(byteBuffer);
    byteBuffer.flip();
    String requestString = new String(byteBuffer.array());
    HttpRequestParser httpRequestParser = new HttpRequestParser();
    HttpRequest httpRequest = httpRequestParser.parse(requestString);
    // check the cache
    WSRVResourceCache<HttpRequest, WSRVResource> cache = WSRVResourceCacheProvider.getInstance().getCache("in-memory");
    WSRVResource resource = cache.get(httpRequest);
    if (resource == null) {
      // retrieve the resource
      final byte[] byteStream;
      String path = httpRequest.getPath();
      File file = new File(path);
      if (!file.exists()) {
        throw new FileNotFoundException(new StringBuilder(path).append(" not found").toString());
      }
      if (file.isFile()) {
        try {
          byteStream = IOUtils.toByteArray(new FileInputStream(file));
        } catch (Exception e) {
          throw new FileNotFoundException(e.getLocalizedMessage());
        }
      } else {
        StringBuilder sb = new StringBuilder();
        for (File f : file.listFiles()) {
          sb.append(f.getName()).append("\n");
        }
        byteStream = sb.toString().getBytes();
      }
      return new WSRVResource() {
        @Override
        public byte[] getBytes() {
          return byteStream;
        }
      };
    }
    byteBuffer.clear(); //make buffer ready for writing
    HttpResponse httpResponse = new HttpResponse();
    httpResponse.setHeader("ETag", String.valueOf(resource.hashCode()));
    httpResponse.setResource(String.valueOf(resource.getBytes()));
    byteBuffer.put(httpResponse.toString().getBytes());
    socket.write(byteBuffer);
    return null;
  }
}
