package com.github.wsrv.nio;

import com.github.wsrv.Resource;
import com.github.wsrv.jetty.repository.FSRequestHandlerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.nio.ByteBuffer;
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
//      String requestString = IOUtils.toString(socket.getInputStream());
      HttpRequestParser httpRequestParser = new HttpRequestParser();
      HttpRequest httpRequest = httpRequestParser.parse(requestString);
      if (requestString.length() > 20 && requestString.startsWith("GET")) {
        byteBuffer.clear(); //make buffer ready for writing
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
        byteBuffer.put(httpResponse.toString().getBytes());
//        byteBuffer.flip();
        socket.getChannel().write(byteBuffer);
        byteBuffer.clear();
      }
    } catch (Exception e) {
      throw e;
    } finally {
      socket.close();
    }
    return null;
  }
}
