package ca.jakegreene.spark.redis.rdd

/**
 * A RedisWriter is responsible for writing values into Redis
 */
trait RedisWriter[V] extends Function2[String, V, Unit] {
  final def apply(key: String, value: V): Unit = write(key, value)
  def write(key: String, value: V): Unit
}