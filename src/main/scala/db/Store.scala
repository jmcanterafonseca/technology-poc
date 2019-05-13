package db


trait Store {
  def insert(entityId:String, entity:String)
  def upsert(entityId:String, entity:String)

  def delete(entityId:String)

  def get(entityId:String):String
  def exists(entityId:String):Boolean

  def init(dbName:String):Store
  def close()

  def destroy()
}
