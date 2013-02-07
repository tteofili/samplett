package com.github.tteofili.fntlearn;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 *
 */
public class NioLineReader {

  private static final byte LINE_END = (byte) ('\n' & 0xFF);
  private ByteBuffer buffer;
  private final FileChannel fc;

  public NioLineReader(FileChannel fc, ByteBuffer buffer) {
    this.fc = fc;
    this.buffer = buffer;
  }

  public NioLineReader(FileChannel fc) {
    this.fc = fc;
  }

  public byte[] nextLine(ByteBuffer byteBuffer) throws Exception {
    long startingPos = fc.position();
    int bytesRead = fc.read(byteBuffer);
    byteBuffer.flip();
    if (bytesRead > 0) {
      for (int i = byteBuffer.position(); i < byteBuffer.limit(); i++) {
        byte c = byteBuffer.get(i);
        if (c == LINE_END) {
          // reach the end
          byte[] car = byteBuffer.array();
          byte[] result = Arrays.copyOf(car, i - byteBuffer.position());
          fc.position(startingPos + i + 1);
          byteBuffer.clear();
          return result;
        }
      }
      byteBuffer.clear();
      return null;
    } else {
      byteBuffer.clear();
      return null;
    }

  }

  public byte[] nextLine() throws Exception {
    return nextLine(buffer);
  }

}
