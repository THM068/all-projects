package models
import akka.util.Timeout
import play.api.inject.ApplicationLifecycle
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.DurationInt
@Singleton
class MongoConnect @Inject() (lifecycle: ApplicationLifecycle) (implicit ec: ExecutionContext) {

  val mongoUri = "mongodb://localhost:27017?authMode=scram-sha1"

  // Connect to the database: Must be done only once per application
  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  lifecycle.addStopHook {() => getConnection().map(_.close()(5.seconds)) }

  def getConnection(): Future[MongoConnection] = parsedUri.flatMap(driver.connect(_))

  implicit def personWriter: BSONDocumentWriter[Person] = Macros.writer[Person]

}



