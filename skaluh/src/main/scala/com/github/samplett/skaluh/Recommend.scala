package com.github.samplett.skaluh

/**
 * class for trying to implement recommendation algorithms in scala
 */

class Recommend {

  val sampleSet = Map(
    "john" -> Map("back to the future" -> 10d, "the social network" -> 9.5d, "point break" -> 4.5d, "bomber" -> 9.5d, "sherlock holmes" -> 8d),
    "paula" -> Map("harry potter and the prisoner of azkaban" -> 7d, "back to the future" -> 3d, "the social network" -> 8d, "bomber" -> 2d, "sherlock holems" -> 8.5d),
    "timothy" -> Map("harry potter and the prisoner of azkaban" -> 7d, "back to the future" -> 3d, "the social network" -> 5.5d, "bomber" -> 2d, "sherlock holems" -> 9.5d)
  )

  def sim_distance(prefs: Map[String, Map[String, Double]], firstPerson: String, secondPerson: String): Double = {
    var si = scala.collection.mutable.ArrayBuffer[String]()

    // get the list of shared items
    for (item <- prefs(firstPerson).keys) if (prefs(secondPerson).contains(item)) si += (item)

    // if there is no shared item then the similarity is 0
    if (si.size == 0) return 0

    // otherwise the euclidean distance based similarity is calculated
    var sumOfSquares = 0d;
    for (item <- si) sumOfSquares += scala.math.pow(prefs(firstPerson)(item) - prefs(secondPerson)(item), 2)
    return 1d / (1d + scala.math.sqrt(sumOfSquares))
  }

}
