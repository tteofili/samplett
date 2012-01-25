package com.github.samplett.skaluh

import io.Source
import java.io.File

/**
 */

class FileAsIterable {
  val src = Source.fromFile(new File("someFile.txt"))

  def iterator = src.getLines()
}