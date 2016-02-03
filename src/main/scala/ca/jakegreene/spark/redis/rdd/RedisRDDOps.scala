package ca.jakegreene.spark.redis.rdd

import org.apache.spark.rdd.RDD

/**
 * Operations for RedisRDDs
 * XXX Maybe enforce that T is a (K, V) at construction
 */
class RedisRDDOps[T](rdd: RDD[T]) {
  def saveToRedis[K, V]()(implicit ev: T <:< (K, V), writer: RedisWriter[V]): Unit = {
    rdd.foreach { tuple =>
      val (key, value) = ev(tuple)
      writer.write(key.toString, value)
    }
  }
}