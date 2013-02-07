package com.github.tteofili.fntlearn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class PiggiaFileParser {

  public Collection<MatchStats> parseFile(File piggiaFile) throws IOException {
    Collection<MatchStats> result = new LinkedList<MatchStats>();
    FileChannel fc = new FileInputStream(piggiaFile).getChannel();
    NioLineReader nioLineReader = new NioLineReader(fc);

    // parse file stream

    if (fc.isOpen())
      fc.close();
    return result;
  }
}
