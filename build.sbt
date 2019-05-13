name := "RocksDB_Experiments"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0"

libraryDependencies += "org.rocksdb" % "rocksdbjni" % "5.5.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.10.2"

mainClass in Compile := Some("main.Launcher")
