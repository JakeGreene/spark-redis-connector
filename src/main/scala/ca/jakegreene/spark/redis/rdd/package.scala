package ca.jakegreene.spark.redis

import org.apache.spark.rdd.RDD
import scala.language.implicitConversions

package object rdd {
  implicit def rddToRedisOps[T](rdd: RDD[T]): RedisRDDOps[T] = new RedisRDDOps(rdd)
}