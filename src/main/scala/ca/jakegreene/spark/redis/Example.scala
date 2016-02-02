package ca.jakegreene.spark.redis

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import ca.jakegreene.spark.redis._

object Example {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf(true).setAppName("spark-redis-example")
    val sc = new SparkContext(config)
    val hashes = sc.redisHashes("Sku-*")
  }
}