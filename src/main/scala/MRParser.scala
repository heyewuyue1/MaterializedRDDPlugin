package cn.edu.ruc.MaterializedRDDPlugin

import org.apache.spark.internal.Logging
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.catalyst.{FunctionIdentifier, TableIdentifier}
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.parser.ParserInterface
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.types.{DataType, StructType}

class MRParser(session: SparkSession, parser: ParserInterface) extends ParserInterface with Logging{
  private val internalSQLPrefix: String = "/* Internal SQL Generated By MRParser */\n"

  def internalExecute(internalRawSql: String): DataFrame = {
    session.sql(internalSQLPrefix.concat(internalRawSql))
  }

  override def parsePlan(sqlText: String): LogicalPlan = {
    logInfo("Parsing SQL with MRParser...")
    var rewriteSqlText = sqlText.trim.toLowerCase
    if (sqlText.startsWith(internalSQLPrefix)) {
      logInfo("SQL generated by MRParser, handling it to the default parser...")
    } else {
      if (rewriteSqlText.startsWith("SELECT")) {
        logInfo("This is a DR statement, Checking if any available materialized RDD can be used...")
        // 接下来的代码要去_mr_meta_info里找是否有可用的RDD
      } else if (sqlText.startsWith("alter table") || sqlText.startsWith("drop table") || sqlText.startsWith("insert")){
        logInfo("This is a DML/DDL statement, disabling corresponding materialized RDD if exists...")
        //接下来的代码就是从sqlText里解析出来动的是哪张表，如果_mr_meta_info里有依赖于该表的物化RDD，就将他的可用性设置为false
      }
    }
    parser.parsePlan(rewriteSqlText)
  }
  override def parseExpression(sqlText: String): Expression =
    parser.parseExpression(sqlText)

  override def parseTableIdentifier(sqlText: String): TableIdentifier =
    parser.parseTableIdentifier(sqlText)

  override def parseFunctionIdentifier(sqlText: String): FunctionIdentifier =
    parser.parseFunctionIdentifier(sqlText)

  override def parseTableSchema(sqlText: String): StructType =
    parser.parseTableSchema(sqlText)

  override def parseDataType(sqlText: String): DataType =
    parser.parseDataType(sqlText)

  override def parseMultipartIdentifier(sqlText: String): Seq[String] =
    parser.parseMultipartIdentifier(sqlText)

  override def parseQuery(sqlText: String): LogicalPlan =
    parser.parseQuery(sqlText)
}