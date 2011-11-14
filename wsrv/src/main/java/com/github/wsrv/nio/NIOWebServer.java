package com.github.wsrv.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
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

    // register a selector on the server socket channel
    selector = SelectorProvider.provider().openSelector();

    // create a new (non blocking) server socket channel
    ServerSocketChannel ssc = ServerSocketChannel.open();

    // get a server socket
    ServerSocket ss = ssc.socket();

    // bind the address
    InetSocketAddress address = new InetSocketAddress(8080);
    ss.bind(address);

    ssc.configureBlocking(false);

    // register accept operations on the registered selector
    ssc.register(selector, SelectionKey.OP_ACCEPT);

  }

  public void run() throws IOException {

    while (selector.select() > 0) {
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> selectionKeyIterator = selectedKeys.iterator();
      while (selectionKeyIterator.hasNext()) {
        SelectionKey k = selectionKeyIterator.next();
        selectionKeyIterator.remove();
        // handle I/O event
        if (k.isValid()) {
          if (k.isAcceptable()) {
            // accept the new connection
            ServerSocketChannel ssc = (ServerSocketChannel) k.channel();
            SocketChannel sc = ssc.accept();
            Socket s = sc.socket();
            requestHandlerService.submit(new SocketHandler(s));
          } else if (k.isReadable() || k.isWritable()) {
            k.interestOps(0);
          }
        }
      }
    }


  }
}
