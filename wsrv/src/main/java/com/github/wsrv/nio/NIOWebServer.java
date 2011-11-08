package com.github.wsrv.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tommaso
 */
public class NIOWebServer {
  private Selector selector;
  private ExecutorService requestHandlerService;

  public void init(ServerConfiguration configuration) throws Exception {

    requestHandlerService = Executors.newFixedThreadPool(configuration.getPoolSize());

    // create a new (non blocking) server socket channel
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);

    // get a server socket
    ServerSocket ss = ssc.socket();
    // bind the address
    InetSocketAddress address = new InetSocketAddress(8080);
    ss.bind(address);

    // register a selector on the server socket channel
    selector = Selector.open();

    // register accept operations on the registered selector
    ssc.register(selector, SelectionKey.OP_ACCEPT);

  }

  public void run() throws IOException {
    while (selector.select() > 0) {
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> selectionKeyIterator = selectedKeys.iterator();
      while (selectionKeyIterator.hasNext()) {
        SelectionKey k = selectionKeyIterator.next();
        // handle I/O event
        if ((k.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
          // accept the new connection
          ServerSocketChannel ssc = (ServerSocketChannel) k.channel();
          SocketChannel sc = ssc.accept();
          sc.configureBlocking(false);
          // register the channel for reading
          SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
          SocketChannel rsc = (SocketChannel) newKey.channel();
          requestHandlerService.submit(new SocketHandler(rsc));
          // remove the accepting key from the selected keys
          selectionKeyIterator.remove();
        } else {
          k.interestOps(0);
        }
      }
    }
  }
}
