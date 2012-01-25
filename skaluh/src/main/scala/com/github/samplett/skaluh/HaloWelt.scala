package com.github.samplett.skaluh

/**
 * first class ever written by myself in Scala from scratch :-)
 */

class HaloWelt(var seed: String) {

  def loopTill(cond: => Boolean)(body: => Unit): Unit = {
    if (cond) {
      body
      loopTill(cond)(body)
    }
  }

  var i = 10
  loopTill(i > 0) {
    println(i)
    i -= 1
  }

  def checkUpper(name: String): Boolean = {
    name.exists(_.isUpper)
  }

  def linesInFile(): Int = {
    val src = scala.io.Source.fromFile(new java.io.File("someFile.txt"))
    val count = src.getLines().foldLeft(0) {
      (i, line) => i + 1
    }
    count
  }

  def iterateFile() {
    val newIterator = new FileAsIterable with Iterable[String]
    newIterator.foreach {
      line => println(line)
    }
  }

  def toList[A](value: A): List[A] = {
    List(value)
  }
}