package models

import play.api.libs.json.{Format, JsError, JsNull, JsString, JsSuccess, Json, OFormat, OWrites, Reads, Writes}
import play.api.mvc.{Request, WrappedRequest}
import reactivemongo.api.bson.{BSONDocumentHandler, BSONObjectID, Macros}

case class Address(number: Int, street: String)
case class Person(name: String, address: Address)

object Model {
  implicit val addressFormat = Json.format[Address]
  implicit val personFormat = Json.format[Person]
}

case class User(id: String, email: String)

case class UserRequest[A](user: User, request: Request[A]) extends WrappedRequest(request)

final case class Customers(_id: Option[BSONObjectID] = None,
                          name: String,
                          email: String,
                          age: Int)

object Customers {
  import models.JsonFormats._
  implicit val customerHandler: BSONDocumentHandler[Customers] = Macros.handler[Customers]

  implicit val customerFormat: OFormat[Customers] = Json.format[Customers]

  // Convert from BSON to JSON
  implicit val customerWriter: OWrites[Customers] = Json.writes[Customers]

}

object JsonFormats {
  // Base formatter for BSONObjectID
  implicit val objectIdFormat: Format[BSONObjectID] = Format(
    Reads {
      case JsString(value) =>
        BSONObjectID.parse(value) match {
          case scala.util.Success(id) => JsSuccess(id)
          case scala.util.Failure(err) => JsError(s"Invalid BSONObjectID: ${err.getMessage}")
        }
      case _ => JsError("Expected JsString for BSONObjectID")
    },
    Writes(id => JsString(id.stringify))
  )

  // Formatter for Option[BSONObjectID]
  implicit val optionObjectIdFormat: Format[Option[BSONObjectID]] = Format(
    Reads {
      case JsNull => JsSuccess(None)
      case value => objectIdFormat.reads(value).map(Some(_))
    },
    Writes {
      case None => JsNull
      case Some(id) => objectIdFormat.writes(id)
    }
  )
}


