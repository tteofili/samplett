package com.github.samplett.skaluh


/**
 * a scala class for common clustering algorithms / utilities
 */
class Cluster {

  val sampleSet = Map(
    "Gothamist" -> Map("china" -> 0, "kids" -> 3, "music" -> 3, "yahoo" -> 0),
    "GigaOM" -> Map("china" -> 6, "kids" -> 0, "music" -> 0, "yahoo" -> 2),
    "Quick Online Tips" -> Map("china" -> 0, "kids" -> 2, "music" -> 2, "yahoo" -> 22)
  );

  def readClusterDatasetFromFile(filePath: String): (Array[String], Array[String], Iterable[Array[Double]]) = {
    val lines = scala.collection.mutable.ArrayBuffer[String]();
    for (line <- scala.io.Source.fromFile(filePath).getLines())
      lines += line;
    val colnames = lines(0).split("\t")
    var rownames = scala.collection.mutable.ArrayBuffer[String]();
    var data = new scala.collection.mutable.HashMap[String, Array[Double]];
    for (line <- lines.drop(1)) {
      // excluding the first row which holds the column names
      val p = line.split("\t");
      val floats = for (item <- p.drop(1)) yield Double.unbox(item);
      data += p(0) -> floats;
      rownames += p(0);
    }
    return (colnames, rownames.toArray, data.values);
  }

  def pearson(f1: Array[Double], f2: Array[Double]): Double = {
    var sum1 = 0;
    for (item <- f1) sum1 += item;

    var sum2 = 0;
    for (item <- f2) sum2 += item;

    var sumSq1 = 0;
    for (item <- f1) sumSq1 += math.pow(item, 2);

    var sumSq2 = 0;
    for (item <- f2) sumSq2 += math.pow(item, 2);

    var pSum = 0;
    for (i <- 0 until f1.length if f2(i)) pSum += f1(i) * f2(i);

    val num = pSum - (sum1 * sum2 / f1.length);
    val den = math.sqrt((sumSq1 - math.pow(sum1, 2) / f1.length) * (sumSq2 - math.pow(sum2, 2) / f2.length))

    return 1 - num / den;
  }

}
