package com.github.samplett.skaluh

import org.junit._
import Assert._

/**
 * testcase for Recommend class
 */
class RecommendTest {

  @Test
  def sim_distanceTest() {
    val r = new Recommend()
    var simDistance = r.sim_distance(r.sampleSet, "paula", "john")
    Console.println("paula and john distance based similarity is : " + simDistance)
    // john and paula are quite far
    assertTrue(simDistance < 0.1)

    // paula and timothy are less far
    simDistance = r.sim_distance(r.sampleSet, "paula", "timothy")
    Console.println("paula and timothy distance based similarity is : " + simDistance)
    assertTrue(simDistance > 0.2)
  }

  @Test
  def sim_pearsonTest() {
    val r = new Recommend()
    var simPearson = r.sim_pearson(r.sampleSet, "paula", "john")
    Console.println("paula and john pearson correlation based similarity is : " + simPearson)
    // john and paula are quite far
    assertTrue(simPearson < 0.1)

    // paula and timothy are less far
    simPearson = r.sim_pearson(r.sampleSet, "paula", "timothy")
    Console.println("paula and timothy pearson correlation based similarity is : " + simPearson)
    assertTrue(simPearson > 0.2)
  }

  @Test
  def topMatchesTestWithDistance() {
    val r = new Recommend()
    val sortedList = r.topMatches(r.sampleSet, "paula", 2, r.sim_distance)
    Console.println(sortedList)
  }

  @Test
  def topMatchesTestWithPearson() {
    val r = new Recommend()
    val sortedList = r.topMatches(r.sampleSet, "paula", 2, r.sim_pearson)
    Console.println(sortedList)
  }

}
