package model
import slick.jdbc.PostgresProfile.api._
import slick.sql.SqlProfile.ColumnOption.SqlType
import play.api.libs.json._
import play.api.i18n.Lang

import java.sql.Timestamp

case class AppStatus(status: String)
case class Error(message: String, code: Int)

object AppStatus {
  implicit val appStatusReads = Json.reads[AppStatus]
  implicit val appStatus = Json.writes[AppStatus]
}

object Error {
  implicit val errorReads = Json.reads[Error]
  implicit val errorWrites = Json.writes[Error]
}

case class Category(
                     category_id: Long,
                     name: String,
                     last_update: Timestamp) {

  def toCategoryMapper(): CategoryMapper = {
    CategoryMapper(
      category_id, name, last_update
    )
  }

}
case class CategoryMapper(
                     category_id: Long,
                     name: String,
                     last_update: Timestamp)
trait TimestampReadWrite {
  implicit val timestampReads: Reads[Timestamp] = {
    implicitly[Reads[Long]].map(new Timestamp(_))
  }

  implicit val timestampWrites: Writes[Timestamp] = {
    implicitly[Writes[Long]].contramap(_.getTime)
  }
}

object CategoryMapper extends TimestampReadWrite{


  implicit val categoryReads = Json.reads[CategoryMapper]
  implicit val categoryWrites = Json.writes[CategoryMapper]
}

case class CategoryMapperList(categoryMapperList: Seq[CategoryMapper])
object CategoryMapperList {
  implicit val categoryListReads = Json.reads[CategoryMapperList]
  implicit val categoryListWrites = Json.writes[CategoryMapperList]
}

class CategoryTable(tag: Tag) extends Table[Category](tag, "category")  {
  def category_id = column[Long]("category_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def last_update = column[Timestamp]("last_update", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

  def * = (category_id, name, last_update) <> (Category.tupled, Category.unapply)
}

case class Actor(actor_id: Long, first_name: String, last_update: Timestamp)

case class LoginData(name: String, username: String)

