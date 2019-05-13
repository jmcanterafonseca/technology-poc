package rocksdb

import org.rocksdb.{Options, RocksDB}


object RocksDBStore extends db.Store {
  private var db: RocksDB = null
  private var initialized = false

  override def init(dbName:String) = {
    if (!initialized) {
      RocksDB.loadLibrary()
      initialized = true
    }

    getDB(dbName)

    this
  }

  override def close() = {
    if (db != null) {
      db.close()
    }
    db = null
  }

  override def delete(entityId: String): Unit = {
    db.delete(entityId.getBytes)
  }

  override def upsert(key: String, entity: String): Unit = {
    if (db == null) {
      throw new RuntimeException("DB not initialized")
    }
    db.put(key.getBytes, entity.getBytes)
  }

  override def insert(key: String, entity: String): Unit = {
    if (db == null) {
      throw new RuntimeException("DB not initialized")
    }

    if (exists(key)) {
      throw new RuntimeException(s"Key already exists: ${key}")
    }

    upsert(key, entity)
  }

  override def get(key: String): String = {
    if (db == null) {
      throw new RuntimeException("DB not initialized")
    }
    new String(db.get(key.getBytes))
  }

  private def getDB(dbName:String): RocksDB = {
    if (db == null) {
      val options = new Options().setCreateIfMissing(true)
      db = RocksDB.open(options, dbName)
    }

    db
  }

  // Need a more recent version of RocksDB
  override def destroy(): Unit = {
    //
  }

  override def exists(entityId: String): Boolean = {
    db.keyMayExist(entityId.getBytes,new java.lang.StringBuilder())
  }
}
