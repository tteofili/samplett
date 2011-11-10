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

    // create a new (non blocking) server socket channel
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);

    // get a server socket
    ServerSocket ss = ssc.socket();
    // bind the address
    InetSocketAddress address = new InetSocketAddress(8080);
    ss.bind(address);

    // register a selector on the server socket channel
    selector = SelectorProvider.provider().openSelector();

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
          if ((k.readyOps() & SelectionKey.OP_ACCEPT)
                  == SelectionKey.OP_ACCEPT) {
            // accept the new connection
            ServerSocketChannel ssc = (ServerSocketChannel) k.channel();
            SocketChannel sc = ssc.accept();
//            sc.configureBlocking(false);
            Socket s = sc.socket();

//            requestHandlerService.submit(new SocketChannelHandler((SocketChannel) k.channel()));
            requestHandlerService.submit(new SocketHandler(s));

            // register the channel for reading
//            sc.register(selector, SelectionKey.OP_READ);
          } else if (k.isReadable() || k.isWritable()) {
            k.interestOps(0);
          }
//          else if ((k.readyOps() & SelectionKey.OP_READ)
//                  == SelectionKey.OP_READ) {
//            requestHandlerService.submit(new SocketChannelHandler((SocketChannel) k.channel()));
//          }
        }
      }
    }


  }
}
