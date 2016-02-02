package ca.jakegreene.spark

import org.apache.spark.SparkContext
import scala.language.implicitConversions

package object redis {
  implicit def contextToRedisOps(context: SparkContext): RedisContextOps = new RedisContextOps(context)  
}