package ca.jakegreene.spark.redis.rdd

import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.Partition
import org.apache.spark.rdd.RDD
import scala.reflect.ClassTag

class RedisRDD[T] private (sc: SparkContext)(implicit ct: ClassTag[T]) extends RDD[T](sc, Seq.empty) {
  override def compute(split: Partition, context: TaskContext): Iterator[T] = ???
  override def getPartitions(): Array[Partition] = ???
}