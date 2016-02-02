package ca.jakegreene.spark.redis

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import ca.jakegreene.spark.redis._
import ca.jakegreene.spark.redis.rdd._

object Example {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf(true).setAppName("spark-redis-example")
    val sc = new SparkContext(config)
    val childByKey = sc.redisHashes("Child:*")
    val childByParentId = childByKey
      .map {
        case (childKey, child) => ("Parent:"+child("parent_id"), child)
      }
      .groupByKey()
    val parentByKey = sc.redisHashes("Parent:*")
    val updatedParents = parentByKey
      .join(childByParentId)
      .mapValues {
        case (parent, child) => rollup(parent, child.toList)
      }
    updatedParents.saveToRedis()
  }
  
  def rollup(parent: Map[String, String], children: Seq[Map[String, String]]): Map[String, String] = ???
}