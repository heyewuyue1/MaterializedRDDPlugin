package cn.edu.ruc.MaterializedRDDPlugin

import org.apache.spark.sql.SparkSessionExtensions

class MRExtension extends (SparkSessionExtensions => Unit) {
  override def apply(extension: SparkSessionExtensions): Unit = {
    extension.injectParser((session, parser) => {
      new MRParser(session, parser)
    })
  }
}
