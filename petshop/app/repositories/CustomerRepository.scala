package repositories

import jakarta.inject.Singleton
import models.Customers
import models.Customers._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{Cursor, ReadPreference}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CustomerRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext){

  private def collection: Future[BSONCollection] = {
    reactiveMongoApi.database.map(_.collection("customers"))
  }

  def findAll: Future[Seq[Customers]] =
    collection.flatMap(
      _.find(BSONDocument(), None)
        .cursor[Customers](ReadPreference.primary)
        .collect[Seq](100, Cursor.FailOnError[Seq[Customers]]())
    )

  def findById(id: BSONObjectID): Future[Option[Customers]] =
    collection.flatMap(
      _.find(BSONDocument("_id" -> id), None)
        .one[Customers]
    )

  def create(customer: Customers): Future[Customers] = {
    val customersWithId = customer.copy(_id = Some(BSONObjectID.generate))
    collection.flatMap(
      _.insert.one(customersWithId).map(_ => customersWithId)
    )
  }

  def delete(id: BSONObjectID): Future[Option[Customers]] = {
    findById(id).flatMap {
      case Some(person) =>
        collection.flatMap(
          _.delete.one(BSONDocument("_id" -> id))
            .map(_ => Some(person))
        )
      case None => Future.successful(None)
    }
  }


}
