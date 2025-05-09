package model.repository

import model.{Category, CategoryTable}
import play.api.db.slick.DatabaseConfigProvider
import play.aclass                   CategoryRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(ec: ExecutionContext)
  extends  HasDatabaseConfigProvider[JdbcProfile]
{
  lazy val categoryTable = TableQuery[CategoryTable]

  def findAll(): Future[Seq[Category]] = db.run { categoryTable.result}

}
