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
    var simDistance = r.sim_distance(r.sampleSet, "paula", "john")
    Console.println("paula and john similarity is : " + simDistance)
    // john and paula are quite far
    assertTrue(simDistance < 0.1);

    // paula and timothy are less far
    simDistance = r.sim_distance(r.sampleSet, "paula", "timothy")
    Console.println("paula and timothy similarity is : " + simDistance)
    assertTrue(simDistance > 0.2);
  }

  @Test
  def sim_pearsonTest() {
    val r = new Recommend();
    var simDistance = r.sim_pearson(r.sampleSet, "paula", "john")
    Console.println("paula and john similarity is : " + simDistance)
    // john and paula are quite far
    assertTrue(simDistance < 0.1);

    // paula and timothy are less far
    simDistance = r.sim_pearson(r.sampleSet, "paula", "timothy")
    Console.println("paula and timothy similarity is : " + simDistance)
    assertTrue(simDistance > 0.2);
  }

}
