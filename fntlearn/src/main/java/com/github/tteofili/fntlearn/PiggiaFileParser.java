package com.github.tteofili.fntlearn;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class PiggiaFileParser {

  public Collection<MatchStats> parseFile(File piggiaFile) throws Exception {
    Collection<MatchStats> result = new LinkedList<MatchStats>();
    FileChannel fc = new FileInputStream(piggiaFile).getChannel();
    NioLineReader nioLineReader = new NioLineReader(fc);

    MatchStatsCollectionBuilder builder = new MatchStatsCollectionBuilder();
    // parse file stream
    byte[] line = null;
    while ((line = nioLineReader.nextLine()) != null) {
      String lineString = new String(line);
      // a line can be
      // nothing
      // team names
      // player stats
      if (lineString.contains("giorn") || lineString.contains("Giorn")) {
        builder.addMatchStats();
      }
    }


    if (fc.isOpen())
      fc.close();
    return result;
  }
}
