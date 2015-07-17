package com.github.samplett.skaluh

import org.junit.Test

/**
 * Tests for Cluster class
 */

class ClusterTest {

  @Test
  def testFileRead() {
    def c = new Cluster()
    val data = c.readClusterDatasetFromFile("src/test/resources/blogdata.txt")
    println(data)
  }
}
