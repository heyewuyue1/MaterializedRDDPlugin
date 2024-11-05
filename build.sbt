ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "MaterializedRDDPlugin",
    idePackagePrefix := Some("cn.edu.ruc.MaterializedRDDPlugin")
  )

resolvers ++= Seq(
  "Aliyun Maven" at "https://maven.aliyun.com/repository/public",
  "Maven Central" at "https://repo1.maven.org/maven2/"
)

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.4.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.4.0"
