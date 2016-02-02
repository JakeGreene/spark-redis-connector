package ca.jakegreene.spark.redis

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

class RedisContextOps(val context: SparkContext) extends AnyVal {
  def redisValues(pattern: String): RDD[(String, String)] = ???
  def redisLists(pattern: String): RDD[(String, List[String])] = ???
  def redisSets(pattern: String): RDD[(String, Set[String])] = ???
  def redisHashes(pattern: String): RDD[(String, Map[String, String])] = ???
  /*
   * XXX Think about the return type of this
   * def redisZSets(pattern: String): RDD[(String, Set[(Int, String)])] = ???
   */
}