package com.github.wsrv.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author tommaso
 */
public class NIOWebServer {

  public void init() throws Exception {
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);
    ServerSocket ss = ssc.socket();
    InetSocketAddress address = new InetSocketAddress(8080);
    ss.bind(address);

    Selector selector = Selector.open();
    SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
    int num = selector.select();
    Set<SelectionKey> selectedKeys = selector.selectedKeys();
    for (SelectionKey k : selectedKeys) {
      // ... deal with I/O event ...
    }
  }
}
