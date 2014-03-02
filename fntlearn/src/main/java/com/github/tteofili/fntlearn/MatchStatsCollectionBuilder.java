package com.github.tteofili.fntlearn;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MatchStatsCollectionBuilder {
  List<MatchStats> collection = new LinkedList<MatchStats>();



  public void addMatchStats() {
    collection.add(new MatchStats());

  }
}
