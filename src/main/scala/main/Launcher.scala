package main

import db.Store
import json.JSONSerializer.serialize
import org.rocksdb.RocksDBException
import rocksdb.RocksDBStore

object Launcher {
  def main(args: Array[String]): Unit = {
    var store: Store = null

    try {
      store = RocksDBStore.init("./db_Rocks/test")

      val totalEntities = 10000

      val now = System.currentTimeMillis()

      for (c <- 1 to totalEntities) {
        val entity = buildEntity(c)

        val entityId = entity("id").asInstanceOf[String]
        store.upsert(entityId, serialize(entity))
      }

      val after = System.currentTimeMillis()

      Console.println(s"Time inserting: ${after - now} milliseconds")

      Console.println(s"Read Entity: ${store.get("urn:ngsi-ld:Test:test-1")}")

      Console.println(s"Exists Entity: ${store.exists("urn:ngsi-ld:Test2:test-1")}")
    }
    catch {
      case ex: RocksDBException => {
        Console.println(ex)
      }
    }
    finally {
      if (store != null) {
        store.close()
      }
    }
  }

  def buildEntity(c: Long) = {
    val entity = Map(
      "id" -> s"urn:ngsi-ld:Test:test-${c}",
      "type" -> "Test",
      "speed" -> Map(
        "type" -> "Property",
        "value" -> c
      )
    )

    entity
  }

}
