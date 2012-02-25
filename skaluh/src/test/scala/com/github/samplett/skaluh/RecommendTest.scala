package com.github.samplett.skaluh

import org.junit._
import Assert._

/**
 * testcase for Recommend class
 */
class RecommendTest {

  @Test
  def sim_distanceTest() {
    val r = new Recommend();
    val simDistance = r.sim_distance(r.sampleSet, "paula", "john")
    // john and paula are quite far
    assertTrue(simDistance < 0.1);
  }

}
