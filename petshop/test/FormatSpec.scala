import models.Address
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
sealed trait Color
case object Red extends Color
case object Green extends Color
case object Blue extends Color

sealed trait TrafficLight
case object RedLight extends TrafficLight
case object GreenLight extends TrafficLight
case object AmberLight extends TrafficLight

object JsNumberAsInt {
  def unapply(value: JsValue): Option[Int] = {
    value match {
      case JsNumber(num) => Some(num.toInt)
      case _ => None
    }
  }
}

class FormatSpec extends AnyFunSuite with Matchers {
  case class TrafficLightUnit(color: TrafficLight)
  case class Shape(name: String, color: Color, size: Int)

  implicit object trafficLightFormat extends Format[TrafficLight] {


    override def reads(json: JsValue): JsResult[TrafficLight] = json match {
      case JsNumberAsInt(0) => JsSuccess(RedLight)
      case JsNumberAsInt(1) => JsSuccess(GreenLight)
      case JsNumberAsInt(3) => JsSuccess(AmberLight)
    }

    override def writes(o: TrafficLight): JsValue = o match {
      case RedLight   => JsNumber(0)
      case GreenLight => JsNumber(1)
      case AmberLight => JsNumber(2)
    }
  }
  implicit object colorFormat extends Format[Color] {

    override def reads(json: JsValue): JsResult[Color] = json match {
      case JsString("red")   => JsSuccess(Red)
      case JsString("green") => JsSuccess(Green)
      case JsString("blue")  => JsSuccess(Blue)
      case other =>
        JsError("error.invalid.color")
    }

    override def writes(color: Color): JsValue = color match {
      case Red   => JsString("red")
      case Green => JsString("green")
      case Blue  => JsString("blue")
    }
  }

  implicit val shapeFormat: Format[Shape] = Json.format[Shape]

  test("create a pet from Json object") {
    import Pet.format
    val pet = Json.fromJson[Pet](
      Json.obj(
        "name" -> "Fluffy",
        "age"  -> 5
      )
    )

    pet.isSuccess shouldBe true
    pet.get shouldBe Pet("Fluffy", 5)
  }

  test("create a shape with a color") {
    val shape = Shape("Circle", Red, 5)
    val json = Json.toJson(shape)

    json shouldBe Json.obj(
      "name"  -> "Circle",
      "color" -> "red",
      "size"  -> 5
    )

    val shapeFromJson = Json.fromJson[Shape](
      Json.obj(
        "name"  -> "Circle",
        "color" -> "red",
        "size"  -> 5
      )
    )

    shapeFromJson.isSuccess shouldBe true
    shapeFromJson.get shouldBe Shape("Circle", Red, 5)
  }

  test("test jspath reads") {
    val numberReads = (JsPath \ "number").read[Int]
    val result = numberReads.reads(
      Json.obj(
        "number" -> 5
      )
    )
    result.isSuccess shouldBe true
    result.get shouldBe 5

    val invalidResult = numberReads.reads(JsNumber(4))
    invalidResult.isError shouldBe true
    invalidResult.asEither.left.getOrElse("") shouldBe Seq((
      JsPath \ "number",
      Seq(JsonValidationError("error.path.missing")))
    )

  }

  test("tuple reads") {
    val constructor = (num: Int, name: String) => Address(num, name)
    val tupleReads= (JsPath \ "number").read[Int] and (JsPath \ "string").read[String]
    val result = tupleReads.tupled.reads(
      Json.obj(
        "number" -> 5,
        "string" -> "hello"
      )
    )

    result.isSuccess shouldBe true
    val (num, str) = result.get
    num shouldBe 5
    str shouldBe "hello"

    val addressReader = tupleReads.apply(constructor)
    val addressResult = addressReader.reads(
      Json.obj(
        "number" -> 5,
        "string" -> "hello"
      )
    )

    addressResult.isSuccess shouldBe true
    val address = addressResult.get
    address shouldBe Address(5, "hello")

    val addressReadsWithAnotherApply = ((JsPath \ "number").read[Int] and (JsPath \ "string").read[String]) (Address.apply _)

    val addressReadsWithAnotherApplyResult = addressReader.reads(
      Json.obj(
        "number" -> 5,
        "string" -> "hello"
      )
    )

    addressReadsWithAnotherApplyResult.isSuccess shouldBe true
    addressReadsWithAnotherApplyResult.get shouldBe Address(5, "hello")

    implicit val addressReadWriteFormat = (
      (JsPath \ "number").format[Int] and
        (JsPath \ "string").format[String]
    )(Address.apply, unlift(Address.unapply))

    val json = Json.toJson(Address(5, "hello"))
    json shouldBe Json.obj(
      "number" -> 5,
      "string" -> "hello"
    )

    val addrss = Json.fromJson[Address](
      Json.obj(
        "number" -> 5,
        "string" -> "hello"
      )
    )

    addrss.isSuccess shouldBe true
    addrss.get shouldBe Address(5, "hello")


  }


}
case class Pet(name: String, age: Int)

object Pet {
  implicit val format: Format[Pet] = Json.format[Pet]
}

