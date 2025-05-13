package learn.test

import zio.Scope
import zio.test.Assertion.equalTo
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assert}

object ErrorTypes extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment with Scope, Any] = suite("Error Types")(
    test("Error Types") {
      val error: String = "An error occurred"
      assert(error)(equalTo("An error occurred"))
    }
  )
}

sealed trait ErrorType
case class NotFoundError(message: String) extends ErrorType
case class ValidationError(message: String) extends ErrorType
