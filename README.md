# Spark Redis Connector

[![Join the chat at https://gitter.im/JakeGreene/spark-redis-connector](https://badges.gitter.im/JakeGreene/spark-redis-connector.svg)](https://gitter.im/JakeGreene/spark-redis-connector?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This library intends to allow you to expose Redis collections as Spark RDDs and Dataframes.

### Example Usage
```scala
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
```
