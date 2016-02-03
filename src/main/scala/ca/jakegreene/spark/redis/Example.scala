package ca.jakegreene.spark.redis

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import ca.jakegreene.spark.redis._
import ca.jakegreene.spark.redis.rdd._

object Example {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf(true)
      .setAppName("spark-redis-example")
      .set("spark.redis.host", "127.0.0.1") // This is the default
      .set("spark.redis.port", "6379") // This is the default
    val sc = new SparkContext(config)
    
    /*
     *  Can read all Redis Hash's matching a pattern into an RDD. Includes the
     *  key as the first element of the tuple to allow for PairRDDFunctions
     */
    val childByKey: RDD[(String, Map[String, String])] = sc.redisHashes("Child:*")
    val childByParentKey = childByKey
      .map {
        case (childKey, child) => (child("parent_key"), child)
      }
      .groupByKey()
    val parentByKey = sc.redisHashes("Parent:*")
    val updatedParents = parentByKey
      .join(childByParentKey)
      .mapValues {
        case (parent, children) => rollup(parent, children.toList)
      }
      
    /*
     *  Implicitly provides the ability to write RDDs into Redis, so long as the RDD contains pairs
     *  and the value of the pair is a supported Redis collection
     */
    updatedParents.saveToRedis()
  }
  
  def rollup(parent: Map[String, String], children: Seq[Map[String, String]]): Map[String, String] = ???
}