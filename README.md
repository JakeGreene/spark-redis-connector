# Spark Redis Connector
This library intends to allow you to expose Redis collections as Spark RDDs and Dataframes.

### Example Usage
```scala
object Example {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf(true).setAppName("spark-redis-example")
    val sc = new SparkContext(config)
    /*
     * Read all Redis Hash's matching the given pattern into an
     * RDD[(String, Map[String, String])]
     */
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
        case (parent, children) => rollup(parent, children.toList)
      }
    // Save the RDD[(String, Map[String, String])] in Redis using the provided keys
    updatedParents.saveToRedis()
  }
  
  def rollup(parent: Map[String, String], children: Seq[Map[String, String]]): Map[String, String] = ???
}
```
