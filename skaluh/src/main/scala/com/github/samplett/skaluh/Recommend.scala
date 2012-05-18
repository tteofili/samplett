package com.github.samplett.skaluh

/**
 * class for trying to implement recommendation algorithms in scala
 */

class Recommend {

  val sampleSet = Map(
    "john" -> Map("back to the future" -> 10d, "the social network" -> 9.5d, "point break" -> 4.5d, "bomber" -> 9.5d, "sherlock holmes" -> 8d),
    "paula" -> Map("harry potter and the prisoner of azkaban" -> 7d, "back to the future" -> 3d, "the social network" -> 8d, "bomber" -> 2d, "sherlock holems" -> 8.5d),
    "jim" -> Map("harry potter and the prisoner of azkaban" -> 4d, "back to the future" -> 5d, "the social network" -> 5d, "bomber" -> 8d, "sherlock holems" -> 1.5d),
    "tom" -> Map("harry potter and the prisoner of azkaban" -> 9d, "back to the future" -> 4d, "the social network" -> 9d, "bomber" -> 1d, "sherlock holems" -> 5d),
    "bob" -> Map("harry potter and the prisoner of azkaban" -> 1d, "back to the future" -> 3d, "the social network" -> 8d, "bomber" -> 2d, "sherlock holems" -> 9d),
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

  def sim_pearson(prefs: Map[String, Map[String, Double]], firstPerson: String, secondPerson: String): Double = {
    var si = scala.collection.mutable.ArrayBuffer[String]()

    // get the list of shared items
    for (item <- prefs(firstPerson).keys) if (prefs(secondPerson).contains(item)) si += (item)

    // if there is no shared item then the similarity is 0
    if (si.size == 0) return 0

    // add up all the preferences
    var sumSq1 = 0d
    for (it <- prefs(firstPerson).keys if si.contains(it))
      sumSq1 += scala.math.pow(prefs(firstPerson)(it), 2)

    var sumSq2 = 0d
    for (it <- prefs(secondPerson).keys if si.contains(it))
      sumSq2 += scala.math.pow(prefs(secondPerson)(it), 2)


    var sum1 = 0d
    for (it <- prefs(firstPerson).keys if si.contains(it))
      sum1 += prefs(firstPerson)(it)

    var sum2 = 0d
    for (it <- prefs(secondPerson).keys if si.contains(it))
      sum2 += prefs(secondPerson)(it)

    var prodSum = 0d
    for (it <- si)
      prodSum += prefs(firstPerson)(it) * prefs(secondPerson)(it)

    val num = prodSum - ((sum1 * sum2) / si.size)
    val den = scala.math.sqrt((sumSq1 - scala.math.pow(sum1, 2) / si.size) * (sumSq2 - scala.math.pow(sum2, 2) / si.size))

    if (den == 0) return 0

    return num / den
  }


  def topMatches(prefs: Map[String, Map[String, Double]], person: String, n: Int, sim: (Map[String, Map[String, Double]],
    String, String) => Double): Set[String] = {
    var scores = new scala.collection.mutable.HashMap[String, Double];
    for (other <- prefs if other._1 != person)
      scores += other._1 -> sim(prefs, person, other._1);
    var sortedList = scores.toList sortBy (_._2)
    return sortedList.reverse.slice(0, n).toMap[String, Double].keySet;
  }

}
