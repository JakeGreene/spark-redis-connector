package ca.jakegreene.spark.redis

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import ca.jakegreene.spark.redis._
import ca.jakegreene.spark.redis.rdd._

object Example {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf(true).setAppName("spark-redis-example")
    val sc = new SparkContext(config)
    val skusByKey = sc.redisHashes("Sku-*")
    val skusByProductId = skusByKey
      .map {
        case (redisKey, sku) => (sku("product_id"), sku)
      }
      .groupByKey()
    val productsByKey = sc.redisHashes("Prod-*")
    val updatedProducts = productsByKey
      .join(skusByProductId)
      .map {
        case (productId, (product, skus)) => (productId, rollup(product, skus.toList))
      }
    updatedProducts.saveToRedis()
  }
  
  def rollup(product: Map[String, String], skus: Seq[Map[String, String]]): Map[String, String] = {
    ???
  }
}