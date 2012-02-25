package com.github.samplett.skaluh

import org.junit._
import Assert._

/**
 *
 */

class RecommendTest {

  @Test
  def sim_distanceTest() {
    val r = new Recommend();
    val simDistance: Double = r.sim_distance(r.sampleSet, "paula", "john")
    Console.println(simDistance)
    assertTrue(simDistance < 0.1);
  }

}
